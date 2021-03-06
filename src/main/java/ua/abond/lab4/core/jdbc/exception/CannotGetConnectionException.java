package ua.abond.lab4.core.jdbc.exception;

public class CannotGetConnectionException extends RuntimeException {

    public CannotGetConnectionException() {
    }

    public CannotGetConnectionException(String message) {
        super(message);
    }

    public CannotGetConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
