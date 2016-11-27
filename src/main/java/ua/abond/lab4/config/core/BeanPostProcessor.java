package ua.abond.lab4.config.core;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName);
}
