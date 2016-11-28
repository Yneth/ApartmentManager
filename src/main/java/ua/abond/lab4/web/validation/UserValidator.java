package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;

import java.util.List;
import java.util.Objects;

public class UserValidator implements Validator<User> {
    @Override
    public List<String> validate(User object) {
        return Validation.<User, String>reader().map(v ->
                v.validate(User::getLogin, Objects::nonNull, "You should provide login.").
                        validate(User::getLogin, login -> login.length() > 6, "Login should be at least 6 characters long.").
                        validate(User::getPassword, Objects::nonNull, "You should provide password.").
                        validate(User::getPassword, pwd -> pwd.length() > 6, "Password should be at least 6 characters long.")
        ).run(object).getExceptions();
    }
}
