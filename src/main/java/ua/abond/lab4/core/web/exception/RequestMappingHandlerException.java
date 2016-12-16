package ua.abond.lab4.core.web.exception;

public class RequestMappingHandlerException extends Exception {

    public RequestMappingHandlerException() {
    }

    public RequestMappingHandlerException(String message) {
        super(message);
    }

    public RequestMappingHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestMappingHandlerException(Throwable cause) {
        super(cause);
    }

    public RequestMappingHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
