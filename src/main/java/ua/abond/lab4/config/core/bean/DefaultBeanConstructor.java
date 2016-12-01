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
    public Object create(ConfigurableBeanFactory context, String beanName, BeanDefinition beanDefinition) {
        if (beanDefinition.isAbstract()) {
            throw new BeanInstantiationException(
                    String.format("Declared bean '%s' of type %s is abstract so it cannot be created.",
                            beanName, beanDefinition.getType().getSimpleName()
                    ));
        }

        try {
            return beanDefinition.getType().newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(
                    String.format("Declared bean '%s' of type '%s' has no default constructor.",
                            beanName, beanDefinition.getType().getSimpleName()
                    ), e
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
