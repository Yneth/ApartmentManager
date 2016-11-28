package ua.abond.lab4.util.validation;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T object);
}
