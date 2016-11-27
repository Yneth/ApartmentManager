package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.ApplicationContext;
import ua.abond.lab4.config.core.ApplicationContextAware;
import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.Ordered;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Arrays.stream(bean.getClass().getDeclaredFields()).
                filter(f -> f.isAnnotationPresent(Inject.class)).
                forEach(f -> inject(bean, f));
        return bean;
    }

    private void inject(Object bean, Field f) {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
        Object injectable = applicationContext.getBean(f.getType());
        try {
            f.set(bean, injectable);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Failed to inject " + injectable + " to " + bean);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
