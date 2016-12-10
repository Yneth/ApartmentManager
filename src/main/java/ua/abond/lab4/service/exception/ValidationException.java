package ua.abond.lab4.service.exception;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ValidationException extends Exception {
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        Objects.requireNonNull(errors);
        this.errors = Collections.unmodifiableList(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
