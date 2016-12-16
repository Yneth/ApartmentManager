package ua.abond.lab4.core;

import ua.abond.lab4.core.bean.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public interface ConfigurableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);

    boolean containsBeanDefinition(Class<?> type);

    int getBeanDefinitionCount();

    BeanDefinition getBeanDefinition(String name);

    BeanDefinition getBeanDefinition(Class<?> type);

    Map<String, BeanDefinition> getBeanDefinitionsOfType(Class<?> type);

    Set<String> getBeanDefinitionNames();

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotation);

    Object createBean(String simpleName, BeanDefinition type);
}
