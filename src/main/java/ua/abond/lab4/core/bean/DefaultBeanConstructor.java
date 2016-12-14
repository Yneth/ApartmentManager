package ua.abond.lab4.core.bean;

import ua.abond.lab4.core.BeanConstructor;
import ua.abond.lab4.core.ConfigurableBeanFactory;
import ua.abond.lab4.core.exception.BeanInstantiationException;
import ua.abond.lab4.core.util.reflection.BeanUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultBeanConstructor implements BeanConstructor {

    @Override
    public boolean canCreate(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        return !beanDefinition.isAbstract()
                && beanDefinition.hasOnlyDefaultConstructor();
    }

    @Override
    public Object create(ConfigurableBeanFactory context, String beanName, BeanDefinition beanDefinition) {
        if (beanDefinition.isAbstract()) {
            throw new BeanInstantiationException(
                    String.format("Declared bean '%s' of type %s is abstract so it cannot be created.",
                            beanName, beanDefinition.getType().getSimpleName()
                    ));
        }
        if (beanDefinition.hasFactoryMethod()) {
            Class declaringClass = beanDefinition.getDeclaringClass();
            Method method = beanDefinition.getFactoryMethod();

            return BeanUtil.create(beanName, getBean(context, declaringClass), method);
        }

        Constructor<?> constructor = Arrays.stream(beanDefinition.getType().getDeclaredConstructors()).
                filter(c -> c.getParameterCount() == 0).
                findFirst().
                orElse(null);
        return BeanUtil.create(beanName, constructor);
    }

    private Object getBean(ConfigurableBeanFactory factory, Class<?> type) {
        if (factory.containsBean(type)) {
            return factory.getBean(type);
        }
        BeanDefinition bd = factory.getBeanDefinition(type);
        return factory.createBean(bd.getType().getSimpleName(), bd);
    }
}
