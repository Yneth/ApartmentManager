package ua.abond.lab4.service;

public interface ClassRegistry<T> {
    void register(Class<?> type, T t);
}
