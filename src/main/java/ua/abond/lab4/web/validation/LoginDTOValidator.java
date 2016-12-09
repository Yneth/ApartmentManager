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
        if (!Objects.isNull(login) && login.length() < 6) {
            errors.add("login.dto.validation.login");
        }
        String password = object.getPassword();
        if (Objects.isNull(password)) {
            errors.add("login.dto.validation.password.null");
        }
        if (!Objects.isNull(password) && password.length() < 6) {
            errors.add("login.dto.validation.password");
        }
        return errors;
    }
}
