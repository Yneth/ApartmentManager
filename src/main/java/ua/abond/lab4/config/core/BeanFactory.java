package ua.abond.lab4.config.core;

import ua.abond.lab4.config.core.exception.BeansException;

import java.util.Map;

public interface BeanFactory {
    boolean containsBean(String name);

    <T> boolean containsBean(Class<T> type);

    Object getBean(String name) throws BeansException;

    <T> T getBean(Class<T> type) throws BeansException;

    <T> Map<String, T> getBeansOfType(Class<T> type);

    void refresh();
}
