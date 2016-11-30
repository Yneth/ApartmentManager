package ua.abond.lab4.config.core.context;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.*;
import ua.abond.lab4.config.core.bean.*;
import ua.abond.lab4.config.core.exception.BeanException;
import ua.abond.lab4.config.core.exception.NoSuchBeanException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {
    private static final Logger logger = Logger.getLogger(AnnotationBeanFactory.class);

    private ClassPathBeanDefinitionScanner scanner;

    private final List<BeanConstructor> beanConstructors = new ArrayList<>();
    private final Set<BeanPostProcessor> beanPostProcessors =
            new TreeSet<>(new OrderedPostProcessorComparator<BeanPostProcessor>());
    private final Set<BeanFactoryPostProcessor> beanFactoryPostProcessors =
            new TreeSet<>(new OrderedPostProcessorComparator<BeanFactoryPostProcessor>());

    private final Map<String, Object> beans = new ConcurrentHashMap<>(16);
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(16);

    public AnnotationBeanFactory() {
        initDefault();
    }

    public AnnotationBeanFactory(String path) {
        Objects.requireNonNull(path);
        initDefault();
        scan(path);
        prepare();
    }

    private void initDefault() {
        this.scanner = new ClassPathBeanDefinitionScanner(this);
        this.beanConstructors.add(new DefaultBeanConstructor());
        this.beanConstructors.add(new InjectAnnotationBeanConstructor());
        this.beanPostProcessors.add(new InjectAnnotationBeanPostProcessor());
        this.beanFactoryPostProcessors.add(new BeanAnnotationBeanFactoryPostProcessor());
        this.beanFactoryPostProcessors.add(new ComponentScanAnnotationBeanFactoryPostProcessor());
    }

    @Override
    public void register(BeanDefinition beanDefinition) {
        logger.debug("Registering BeanDefinition of " + beanDefinition.getType() + " type.");
        beanDefinitions.put(beanDefinition.getType().getSimpleName(), beanDefinition);
    }

    @Override
    public boolean containsBean(String name) {
        return beans.containsKey(name);
    }

    @Override
    public <T> boolean containsBean(Class<T> type) {
        return beans.values().stream().
                filter(o -> type.isAssignableFrom(o.getClass())).
                map(o -> true).
                findFirst().
                orElse(false);
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
            logger.error("Provided paths are not correct", e);
        } catch (ClassNotFoundException e) {
            logger.warn("Failed to find class. ", e);
        }
    }

    @Override
    public final void prepare() {
        // TODO: clear beans or somewhat
        beans.put(this.getClass().getSimpleName(), this);

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
        }).forEach(e -> createBean(e.getKey(), e.getValue()));
    }

    private void invokeBeanFactoryPostProcessors() {
        beanFactoryPostProcessors.stream().
                forEach(bfpp -> bfpp.postProcess(this));
    }

    private void registerBeanFactoryPostProcessors() {
        getBeanDefinitionsOfTypeStream(BeanFactoryPostProcessor.class).forEach(e -> {
            beanFactoryPostProcessors.add((BeanFactoryPostProcessor) createBean(e.getKey(), e.getValue(), false));
        });
    }

    private void registerBeanCreators() {
        getBeanDefinitionsOfTypeStream(BeanConstructor.class).forEach(e -> {
            beanConstructors.add((BeanConstructor) createBean(e.getKey(), e.getValue(), false));
        });
    }

    private void registerBeanPostProcessors() {
        getBeanDefinitionsOfTypeStream(BeanPostProcessor.class).forEach(e -> {
            beanPostProcessors.add((BeanPostProcessor) createBean(e.getKey(), e.getValue(), false));
        });
    }

    private <T> Stream<Map.Entry<String, BeanDefinition>> getBeanDefinitionsOfTypeStream(Class<T> type) {
        return beanDefinitions.entrySet().stream().
                filter(e -> type.isAssignableFrom(e.getValue().getType()));
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitions.containsKey(beanName);
    }

    @Override
    public boolean containsBeanDefinition(Class<?> type) {
        return beanDefinitions.values().stream().
                map(BeanDefinition::getType).
                filter(type::isAssignableFrom).
                findFirst().
                map(c -> true).
                orElse(false);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitions.size();
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(Class<?> type) {
        return beanDefinitions.values().stream().
                filter(bd -> type.isAssignableFrom(bd.getType())).
                findFirst().
                orElseThrow(() ->
                        new NoSuchBeanException("Failed to find bean definition with type '" +
                                type.getSimpleName() + "'")
                );
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitionsOfType(Class<?> type) {
        return getBeanDefinitionsOfTypeStream(type).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotation) {
        return beans.entrySet().stream().
                filter(e -> e.getValue().getClass().isAnnotationPresent(annotation)).
                collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Set<String> getBeanDefinitionNames() {
        return beanDefinitions.keySet();
    }

    @Override
    public Object createBean(String simpleName, BeanDefinition beanDefinition) {
        if (containsBean(simpleName)) {
            return getBean(simpleName);
        }
        logger.debug("Trying to create new instance of '" + simpleName + "'.");
        return createBean(simpleName, beanDefinition, true);
    }

    public Object createBean(String simpleName, BeanDefinition beanDefinition, boolean putToMap) {
        Object bean = beanConstructors.stream().
                filter(bc -> bc.canCreate(this, simpleName, beanDefinition)).
                findFirst().
                map(bc -> bc.create(this, simpleName, beanDefinition)).
                // TODO change exception
                        orElseThrow(IllegalStateException::new);

        for (BeanPostProcessor bpp : beanPostProcessors) {
            bean = bpp.postProcessBeforeInitialization(this, bean, simpleName);
        }

        if (putToMap) {
            beans.put(simpleName, bean);
        }
        return bean;
    }
}
