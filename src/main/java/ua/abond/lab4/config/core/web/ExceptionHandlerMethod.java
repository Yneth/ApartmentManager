package ua.abond.lab4.config.core.web;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public void invoke(HttpServletRequest req, HttpServletResponse resp, ExceptionHandlerData data)
            throws InvocationTargetException {
        try {
            method.invoke(declaringObject, req, resp, data);
        } catch (IllegalAccessException e) {
            logger.error(String.format("Failed to invoke ExceptionHandler for %s.%s",
                    declaringObject.getClass().getSimpleName(), method.getName()
            ));
        }
    }
}
