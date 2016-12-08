package ua.abond.lab4.config.core.web;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExceptionHandlerMethod<E extends Throwable> {
    private static final Logger logger = Logger.getLogger(ExceptionHandlerMethod.class);

    private final Method method;
    private final Object declaringObject;

    public ExceptionHandlerMethod(Method method, Object declaringObject) {
        this.method = method;
        this.declaringObject = declaringObject;
    }

    public void invoke(ExceptionHandlerData data)
            throws InvocationTargetException {
        try {
            method.invoke(declaringObject, data);
        } catch (IllegalAccessException e) {
            logger.error(String.format("Failed to invoke ExceptionHandler for %s.%s",
                    declaringObject.getClass().getSimpleName(), method.getName()
            ));
        }
    }
}
