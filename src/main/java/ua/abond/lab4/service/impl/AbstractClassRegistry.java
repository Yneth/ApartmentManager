package ua.abond.lab4.service.impl;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractClassRegistry<T> {
    protected final Map<Class<?>, T> registry;

    public AbstractClassRegistry(Map<Class<?>, T> registry) {
        this.registry = registry;
    }

    public void register(Class<?> type, T t) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(t);
        registry.put(type, t);
    }
}
