package ua.abond.lab4.config.core;

import ua.abond.lab4.config.core.bean.BeanDefinition;

public interface BeanDefinitionRegistry {
    void register(BeanDefinition beanDefinition);
}
