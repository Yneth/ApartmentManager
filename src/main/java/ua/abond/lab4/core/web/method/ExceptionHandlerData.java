package ua.abond.lab4.core.web.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ExceptionHandlerData {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Method handler;
    private final Throwable exception;

    public ExceptionHandlerData(HttpServletRequest request, HttpServletResponse response,
                                Method handler, Throwable exception) {
        this.request = request;
        this.response = response;
        this.handler = handler;
        this.exception = exception;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Method getHandler() {
        return handler;
    }

    public Throwable getException() {
        return exception;
    }
}
