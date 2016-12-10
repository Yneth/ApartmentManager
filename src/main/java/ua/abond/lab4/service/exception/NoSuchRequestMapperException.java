package ua.abond.lab4.service.exception;

public class NoSuchRequestMapperException extends RuntimeException {
    public NoSuchRequestMapperException(String message) {
        super(message);
    }
}
