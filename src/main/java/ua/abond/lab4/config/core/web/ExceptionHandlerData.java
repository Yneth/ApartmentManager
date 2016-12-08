package ua.abond.lab4.config.core.web;

import java.lang.reflect.Method;

public class ExceptionHandlerData {
    private final Method handler;
    private final Class<? extends Throwable> exception;

    public ExceptionHandlerData(Method handler, Class<? extends Throwable> exception) {
        this.handler = handler;
        this.exception = exception;
    }

    public Method getHandler() {
        return handler;
    }

    public Class<? extends Throwable> getException() {
        return exception;
    }
}
