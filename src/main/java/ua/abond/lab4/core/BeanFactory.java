package ua.abond.lab4.core;

import java.util.Map;

public interface BeanFactory {
    boolean containsBean(String name);

    <T> boolean containsBean(Class<T> type);

    Object getBean(String name);

    <T> T getBean(Class<T> type);

    <T> Map<String, T> getBeansOfType(Class<T> type);

    void refresh();
}
