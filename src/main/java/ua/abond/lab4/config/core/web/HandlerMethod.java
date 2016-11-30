package ua.abond.lab4.config.core.web;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerMethod {
    private static final Logger logger = Logger.getLogger(HandlerMethod.class);

    private final Object declaringClass;
    private final Method method;

    public HandlerMethod(Object declaringClass, Method method) {
        Objects.requireNonNull(declaringClass);
        Objects.requireNonNull(method);
        this.declaringClass = declaringClass;
        this.method = method;
    }

    public void handle(Object... args)
            throws InvocationTargetException {
        try {
            method.invoke(declaringClass, args);
        } catch (IllegalAccessException e) {
            logger.error("Failed to invoke RequestHandler for " + method.getName());
        } catch (InvocationTargetException e) {
            throw e;
        }
    }
}
