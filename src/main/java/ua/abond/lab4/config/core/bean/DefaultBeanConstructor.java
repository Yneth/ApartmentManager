package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanConstructor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;
import ua.abond.lab4.util.reflection.BeanUtil;

import java.lang.reflect.Constructor;
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

        Constructor<?> constructor = Arrays.stream(beanDefinition.getType().getDeclaredConstructors()).
                filter(c -> c.getParameterCount() == 0).
                findFirst().
                orElse(null);
        return BeanUtil.create(beanName, constructor);
    }
}
