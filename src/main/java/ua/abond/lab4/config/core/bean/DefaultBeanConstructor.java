package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanConstructor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.util.Arrays;

public class DefaultBeanConstructor implements BeanConstructor {

    @Override
    public boolean canCreate(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        return !beanDefinition.isAbstract()
                && hasNoDefaultConstructor(beanDefinition.getType());
    }

    @Override
    public Object create(ConfigurableBeanFactory context, String bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isAbstract()) {
            throw new BeanInstantiationException("Declared bean \"" + bean + "\" of type " + beanDefinition.getType() +
                    " is abstract so it cannot be created.");
        }
        try {
            return beanDefinition.getType().newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(
                    "Declared bean \"" + bean + "\" of type " + beanDefinition.getType() + " has no default constructor",
                    e
            );
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(e);
        }
    }

    private boolean hasNoDefaultConstructor(Class<?> type) {
        return Arrays.stream(type.getConstructors()).
                anyMatch(c -> c.getParameterCount() == 0);
    }
}
