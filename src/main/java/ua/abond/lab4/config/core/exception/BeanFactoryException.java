package ua.abond.lab4.config.core.exception;

public class BeanFactoryException extends RuntimeException {

    public BeanFactoryException() {
    }

    public BeanFactoryException(String message) {
        super(message);
    }

    public BeanFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanFactoryException(Throwable cause) {
        super(cause);
    }

    public BeanFactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
