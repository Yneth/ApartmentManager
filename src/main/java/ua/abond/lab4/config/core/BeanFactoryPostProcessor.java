package ua.abond.lab4.config.core;

public interface BeanFactoryPostProcessor {
    void postProcess(ConfigurableBeanFactory context);
}
