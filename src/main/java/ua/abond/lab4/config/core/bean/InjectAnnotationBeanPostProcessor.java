package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.Ordered;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        Arrays.stream(bean.getClass().getDeclaredFields()).
                filter(f -> f.isAnnotationPresent(Inject.class)).
                forEach(f -> inject(factory, bean, f));
        return bean;
    }

    private void inject(BeanFactory factory, Object bean, Field f) {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
        Object injectable = factory.getBean(f.getType());
        try {
            f.set(bean, injectable);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Failed to inject " + injectable + " to " + bean);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
