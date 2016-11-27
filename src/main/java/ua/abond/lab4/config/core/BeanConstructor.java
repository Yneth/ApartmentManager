package ua.abond.lab4.config.core;

import ua.abond.lab4.config.core.bean.BeanDefinition;

public interface BeanConstructor {
    boolean canCreate(ApplicationContext context, String bean, BeanDefinition beanDefinition);
    Object create(ApplicationContext context, String bean, BeanDefinition beanDefinition);
}
