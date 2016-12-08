package ua.abond.lab4.config.core.web.method;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.web.exception.RequestMappingHandlerException;

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

    public void handle(Object... args) throws RequestMappingHandlerException {
        try {
            method.invoke(declaringObject, args);
        } catch (IllegalAccessException e) {
            logger.error("Failed to invoke RequestHandler for " + method.getName());
        } catch (InvocationTargetException e) {
            throw new RequestMappingHandlerException(e.getTargetException());
        }
    }

    public Object getDeclaringObject() {
        return declaringObject;
    }

    public Method getMethod() {
        return method;
    }
}
