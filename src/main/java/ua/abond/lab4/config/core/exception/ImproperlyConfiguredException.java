package ua.abond.lab4.config.core.exception;

public class ImproperlyConfiguredException extends BeanFactoryException {

    public ImproperlyConfiguredException() {
    }

    public ImproperlyConfiguredException(String message) {
        super(message);
    }

    public ImproperlyConfiguredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImproperlyConfiguredException(Throwable cause) {
        super(cause);
    }

    public ImproperlyConfiguredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
