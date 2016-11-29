package ua.abond.lab4.config.core.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerMethod {
    private final Object declaringClass;
    private final Method method;

    public HandlerMethod(Object declaringClass, Method method) {
        Objects.requireNonNull(declaringClass);
        Objects.requireNonNull(method);
        this.declaringClass = declaringClass;
        this.method = method;
    }

    public void handle(Object... args) {
        try {
            method.invoke(declaringClass, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
