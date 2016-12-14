package ua.abond.lab4.config.core.bean;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.BeanConstructor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;
import ua.abond.lab4.config.core.exception.ImproperlyConfiguredException;
import ua.abond.lab4.util.reflection.BeanUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectAnnotationBeanConstructor implements BeanConstructor {
    private static final Logger logger = Logger.getLogger(InjectAnnotationBeanConstructor.class);

    private Set<String> visited = new HashSet<>();

    @Override
    public boolean canCreate(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isInnerClass()) {
            return false;
        }
        List<Constructor<?>> constructors = getInjectableConstructors(beanDefinition);

        if (beanDefinition.hasFactoryMethod()) {
            return Arrays.stream(beanDefinition.getFactoryMethod().getParameterTypes()).
                    allMatch(context::containsBeanDefinition);
        }
        if (constructors.size() > 1) {
            throw new ImproperlyConfiguredException(
                    String.format(
                            "Bean '%s' has more than 1 injectable constructor.",
                            bean
                    )
            );
        }
        if (constructors.isEmpty()) {
            return false;
        }
        Constructor ctr = constructors.get(0);
        return Arrays.stream(ctr.getParameterTypes()).allMatch(context::containsBeanDefinition);
    }


    @Override
    public Object create(ConfigurableBeanFactory context, String beanName, BeanDefinition beanDefinition) {
        logger.debug(String.format("Creating new instance of '%s'.", beanName));
        if (visited.contains(beanName)) {
            throw new ImproperlyConfiguredException(String.format("Bean with name '%s' has cyclic dependency.", beanName));
        }
        visited.add(beanName);
        if (beanDefinition.hasFactoryMethod()) {
            Method method = beanDefinition.getFactoryMethod();

            Object[] args = Arrays.stream(method.getParameterTypes()).
                    map(cls -> getBean(context, cls)).
                    toArray(Object[]::new);
            return BeanUtil.create(beanName, getBean(context, beanDefinition.getDeclaringClass()), method, args);
        }

        Constructor<?> constructor = getInjectableConstructorsStream(beanDefinition).
                findFirst().orElseThrow(() ->
                new BeanInstantiationException(String.format("No constructor available for '%s' bean.", beanName))
        );
        Object[] args = Arrays.stream(constructor.getParameterTypes()).
                map(cls -> getBean(context, cls)).
                toArray(Object[]::new);
        return BeanUtil.create(beanName, constructor, args);
    }

    private Object getBean(ConfigurableBeanFactory factory, Class<?> type) {
        if (factory.containsBean(type)) {
            return factory.getBean(type);
        }
        BeanDefinition bd = factory.getBeanDefinition(type);
        return factory.createBean(bd.getType().getSimpleName(), bd);
    }

    private List<Constructor<?>> getInjectableConstructors(BeanDefinition beanDefinition) {
        return getInjectableConstructorsStream(beanDefinition).
                collect(Collectors.toList());
    }

    private Stream<? extends Constructor<?>> getInjectableConstructorsStream(BeanDefinition beanDefinition) {
        return Arrays.stream(beanDefinition.getType().getDeclaredConstructors()).
                filter(c -> c.isAnnotationPresent(Inject.class));
    }
}
