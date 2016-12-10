package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.service.Validator;
import ua.abond.lab4.service.exception.NoSuchValidatorException;
import ua.abond.lab4.service.exception.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ValidationServiceImpl extends AbstractClassRegistry<Validator>
        implements ValidationService {

    public ValidationServiceImpl() {
        super(new HashMap<>());
    }

    @Override
    public void register(Class<?> type, Validator validator) {
        registry.put(type, validator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<String> tryValidate(T object) {
        Objects.requireNonNull(object);

        Class<?> type = object.getClass();
        if (!registry.containsKey(type)) {
            throw new NoSuchValidatorException("Could not find validator for '" + object.getClass() + "'");
        }
        return registry.get(type).validate(object);
    }

    @Override
    public <T> void validate(T object) throws ValidationException {
        List<String> errors = tryValidate(object);
        if (!Objects.isNull(errors) && !errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
