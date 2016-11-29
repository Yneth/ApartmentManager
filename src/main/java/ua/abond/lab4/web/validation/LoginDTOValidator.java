package ua.abond.lab4.web.validation;

import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;
import ua.abond.lab4.web.dto.LoginDTO;

import java.util.List;
import java.util.Objects;

public class LoginDTOValidator implements Validator<LoginDTO> {

    @Override
    public List<String> validate(LoginDTO object) {
        return Validation.<LoginDTO, String>reader().map(v ->
                v.validate(LoginDTO::getLogin, Objects::nonNull, "Login cannot be empty.").
                        validate(LoginDTO::getLogin, login -> login.length() > 6, "Login should contain more than 6 characters.").
                        validate(LoginDTO::getPassword, pwd -> pwd.length() > 6, "Password should contain more than 6 characters.")
        ).run(object).getExceptions();
    }
}
