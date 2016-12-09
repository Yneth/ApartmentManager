package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserValidator implements Validator<User> {
    @Override
    public List<String> validate(User object) {
        List<String> errors = new ArrayList<>();
        String login = object.getLogin();
        if (Objects.isNull(login)) {
            errors.add("You should provide login.");
        } else if (login.length() < 6) {
            errors.add("Login length should be greater than 6 characters long.");
        }
        String password = object.getPassword();
        if (Objects.isNull(password)) {
            errors.add("Password cannot be empty.");
        } else if (password.length() < 6) {
            errors.add("Password length should be greater than 6 characters long.");
        }
        return errors;
    }
}
