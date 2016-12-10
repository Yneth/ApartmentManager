package ua.abond.lab4.service;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T object);
}
