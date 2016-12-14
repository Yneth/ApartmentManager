package ua.abond.lab4.core;

public interface BeanFactoryPostProcessor {
    void postProcess(ConfigurableBeanFactory context);
}
