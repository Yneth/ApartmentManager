package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanConstructor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectAnnotationBeanConstructor implements BeanConstructor {

    @Override
    public boolean canCreate(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        List<Constructor<?>> constructors = getInjectableConstructors(beanDefinition);

        if (constructors.size() > 1) {
            return false;
        }
        if (constructors.isEmpty()) {
            return true;
        }
        Constructor ctr = constructors.get(0);
        return Arrays.stream(ctr.getParameterTypes()).allMatch(context::containsBeanDefinition);
    }


    @Override
    public Object create(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        if (beanDefinition.hasFactoryMethod()) {
            try {
                Method method = beanDefinition.getFactoryMethod();
                method.setAccessible(true);
                return method.invoke(getBean(context, beanDefinition.getDeclaringClass()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        Constructor<?> constructor = getInjectableConstructorsStream(beanDefinition).
                findFirst().orElseThrow(() -> new BeanInstantiationException("No constructor available."));
        Object[] args = Arrays.stream(constructor.getParameterTypes()).
                map(cls -> getBean(context, cls)).
                collect(Collectors.toList()).toArray();
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new BeanInstantiationException("Is " + bean + " abstract?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Are " + bean + "'s constructors private?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException("Constructor threw an exception.", e);
        }
    }

    private Object getBean(ConfigurableBeanFactory factory, Class<?> type) {
        if (factory.containsBean(type)) {
            return factory.getBean(type);
        }
        BeanDefinition bd = factory.getBeanDefinition(type);
        return factory.createBean(bd.getType().getSimpleName(), bd);
    }

    private Object getBean(ConfigurableBeanFactory factory, String beanName) {
        if (factory.containsBean(beanName)) {
            return factory.getBean(beanName);
        }
        BeanDefinition bd = factory.getBeanDefinition(beanName);
        return factory.createBean(bd.getType().getSimpleName(), bd);
    }

    private List<Constructor<?>> getInjectableConstructors(BeanDefinition beanDefinition) {
        return getInjectableConstructorsStream(beanDefinition).
                collect(Collectors.toList());
    }

    private Stream<? extends Constructor<?>> getInjectableConstructorsStream(BeanDefinition beanDefinition) {
        return Arrays.stream(beanDefinition.getType().getConstructors()).
                filter(c -> c.isAnnotationPresent(Inject.class));
    }
}
