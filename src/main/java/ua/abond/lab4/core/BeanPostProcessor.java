package ua.abond.lab4.core;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName);

    Object postProcessAfterInitialization(ConfigurableBeanFactory factory, Object bean, String simpleName);
}
