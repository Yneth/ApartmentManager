package ua.abond.lab4.config.core;

import ua.abond.lab4.config.core.exception.BeanException;

import java.util.Map;

public interface ApplicationContext {
    boolean containsBean(String name);

    Object getBean(String name) throws BeanException;

    <T> T getBean(Class<T> type) throws BeanException;

    <T> Map<String, T> getBeansOfType(Class<T> type);

    void prepare();
}
