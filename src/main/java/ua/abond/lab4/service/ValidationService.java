package ua.abond.lab4.service;

import ua.abond.lab4.service.exception.ValidationException;

import java.util.List;

public interface ValidationService extends ClassRegistry<Validator> {
    <T> List<String> tryValidate(T object);

    <T> void validate(T obj) throws ValidationException;
}
