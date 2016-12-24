package ua.abond.lab4.core.web.method;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ExceptionHandlerMethod {
    private static final Logger logger = Logger.getLogger(ExceptionHandlerMethod.class);

    private final Method method;
    private final Object declaringObject;

    public ExceptionHandlerMethod(Method method, Object declaringObject) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(declaringObject);

        this.method = method;
        this.declaringObject = declaringObject;
    }

    public Object invoke(ExceptionHandlerData data)
            throws InvocationTargetException {
        try {
            return method.invoke(declaringObject, data);
        } catch (IllegalAccessException e) {
            logger.error(String.format("Failed to invoke ExceptionHandler for %s.%s",
                    declaringObject.getClass().getSimpleName(), method.getName()
            ), e);
        }
        return null;
    }
}
