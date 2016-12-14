package ua.abond.lab4.core;

import ua.abond.lab4.core.bean.BeanDefinition;

public interface BeanDefinitionRegistry {
    void register(BeanDefinition beanDefinition);
}
