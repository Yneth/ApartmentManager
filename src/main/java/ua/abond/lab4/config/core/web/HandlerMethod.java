package ua.abond.lab4.config.core.web;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerMethod {
    private static final Logger logger = Logger.getLogger(HandlerMethod.class);

    private final Object declaringObject;
    private final Method method;

    public HandlerMethod(Object declaringObject, Method method) {
        Objects.requireNonNull(declaringObject);
        Objects.requireNonNull(method);
        this.declaringObject = declaringObject;
        this.method = method;
    }

    public void handle(Object... args)
            throws InvocationTargetException {
        try {
            method.invoke(declaringObject, args);
        } catch (IllegalAccessException e) {
            logger.error("Failed to invoke RequestHandler for " + method.getName());
        } catch (InvocationTargetException e) {
            throw e;
        }
    }
}
