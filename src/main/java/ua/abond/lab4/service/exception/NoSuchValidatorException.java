package ua.abond.lab4.service.exception;

public class NoSuchValidatorException extends RuntimeException {
    public NoSuchValidatorException(String message) {
        super(message);
    }
}
