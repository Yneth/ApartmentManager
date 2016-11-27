package ua.abond.lab4.config.core.context;

import ua.abond.lab4.config.core.*;
import ua.abond.lab4.config.core.bean.*;
import ua.abond.lab4.config.core.exception.BeanException;
import ua.abond.lab4.config.core.exception.NoSuchBeanException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationApplicationContext implements ApplicationContext, BeanDefinitionRegistry {
    private ClassPathBeanDefinitionScanner scanner;

    private final List<BeanConstructor> beanConstructors = new ArrayList<>();
    private final Set<BeanPostProcessor> beanPostProcessors = new TreeSet<>(new BeanPostProcessorComparator());
    private final Set<BeanFactoryPostProcessor> beanFactoryPostProcessors = new HashSet<>();

    private final Map<String, Object> beans = new ConcurrentHashMap<>(16);
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(16);

    public AnnotationApplicationContext() {
        this.scanner = new ClassPathBeanDefinitionScanner(this);
        this.beanConstructors.add(new DefaultBeanConstructor());
        this.beanConstructors.add(new InjectAnnotationBeanConstructor());
        InjectAnnotationBeanPostProcessor injectAnnotationBeanPostProcessor = new InjectAnnotationBeanPostProcessor();
        injectAnnotationBeanPostProcessor.setApplicationContext(this);
        this.beanPostProcessors.add(injectAnnotationBeanPostProcessor);
    }

    public AnnotationApplicationContext(String path) {
        this();
        scan(path);
        prepare();
    }

    @Override
    public void register(Class<?> clazz) {
        beanDefinitions.put(clazz.getSimpleName(), new BeanDefinition(clazz));
    }

    @Override
    public boolean containsBean(String name) {
        return beans.containsKey(name);
    }

    @Override
    public Object getBean(String name) throws BeanException {
        Objects.requireNonNull(name);
        if (!containsBean(name)) {
            throw new NoSuchBeanException();
        }
        return beans.get(name);
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeanException {
        Objects.requireNonNull(type);
        return (T) beans.values().stream().
                filter(o -> type.isAssignableFrom(o.getClass())).
                findFirst().
                orElseThrow(() -> new NoSuchBeanException("Bean not found: " + type.getSimpleName()));
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Objects.requireNonNull(type);
        return beans.entrySet().stream().
                filter(kv -> type.isAssignableFrom(kv.getValue().getClass())).
                collect(Collectors.toMap(Map.Entry::getKey, e -> (T) e.getValue()));
    }

    public final void scan(String... paths) {
        try {
            scanner.scan(paths);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void prepare() {
        // TODO: clear beans or somewhat
        registerBeanFactoryPostProcessors();
        invokeBeanFactoryPostProcessors();
        registerBeanPostProcessors();
        registerBeanCreators();
        createBeans();
    }

    private void createBeans() {
        beanDefinitions.entrySet().stream().filter(e -> {
            BeanDefinition bd = e.getValue();
            Class cls = bd.getType();
            return !BeanFactoryPostProcessor.class.isAssignableFrom(cls)
                    && !BeanPostProcessor.class.isAssignableFrom(cls)
                    && !BeanConstructor.class.isAssignableFrom(cls);
        }).forEach(this::createBean);
    }

    private Object createBean(Map.Entry<String, BeanDefinition> e) {
        return createBean(e, true);
    }

    private Object createBean(Map.Entry<String, BeanDefinition> e, boolean putToMap) {
        Object bean = beanConstructors.stream().
                filter(bc -> bc.canCreate(this, e.getKey(), e.getValue())).
                findFirst().
                map(bc -> bc.create(this, e.getKey(), e.getValue())).
                // TODO change exception
                        orElseThrow(IllegalStateException::new);

        for (BeanPostProcessor bpp : beanPostProcessors) {
            bean = bpp.postProcessBeforeInitialization(bean, e.getKey());
        }

        if (putToMap) {
            beans.put(e.getKey(), bean);
        }
        return bean;
    }

    private void invokeBeanFactoryPostProcessors() {
        beanFactoryPostProcessors.stream().
                forEach(bfpp -> bfpp.postProcess(this));
    }

    private void registerBeanFactoryPostProcessors() {
        getBeanDefinitionsOfType(BeanFactoryPostProcessor.class).forEach(e -> {
            beanFactoryPostProcessors.add((BeanFactoryPostProcessor) createBean(e, false));
        });
    }

    private void registerBeanCreators() {
        getBeanDefinitionsOfType(BeanConstructor.class).forEach(e -> {
            beanConstructors.add((BeanConstructor) createBean(e, false));
        });
    }

    private void registerBeanPostProcessors() {
        getBeanDefinitionsOfType(BeanPostProcessor.class).forEach(e -> {
            beanPostProcessors.add((BeanPostProcessor) createBean(e, false));
        });
    }

    private <T> Stream<Map.Entry<String, BeanDefinition>> getBeanDefinitionsOfType(Class<T> type) {
        return beanDefinitions.entrySet().stream().
                filter(e -> type.isAssignableFrom(e.getValue().getClass()));
    }
}
