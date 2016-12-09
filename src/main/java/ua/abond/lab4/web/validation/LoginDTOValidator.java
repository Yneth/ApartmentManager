package ua.abond.lab4.web.validation;

import ua.abond.lab4.util.validation.Validator;
import ua.abond.lab4.web.dto.LoginDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginDTOValidator implements Validator<LoginDTO> {

    @Override
    public List<String> validate(LoginDTO object) {
        List<String> errors = new ArrayList<>();
        String login = object.getLogin();
        if (Objects.isNull(login)) {
            errors.add("login.dto.validation.login.null");
        }
        String password = object.getPassword();
        if (Objects.isNull(password)) {
            errors.add("login.dto.validation.password.null");
        }
        return errors;
    }
}
