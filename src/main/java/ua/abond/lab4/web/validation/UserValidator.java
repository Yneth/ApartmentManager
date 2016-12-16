package ua.abond.lab4.web.validation;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserValidator implements Validator<User> {

    @Override
    public List<String> validate(User object) {
        List<String> errors = new ArrayList<>();
        String login = object.getLogin();
        if (Objects.isNull(login)) {
            errors.add("user.validation.login.null");
        }
        if (!Objects.isNull(login) && login.length() < 6) {
            errors.add("user.validation.login");
        }
        String password = object.getPassword();
        if (Objects.isNull(password)) {
            errors.add("user.validation.password.null");
        }
        if (!Objects.isNull(password) && password.length() < 6) {
            errors.add("user.validation.password");
        }
        return errors;
    }
}
