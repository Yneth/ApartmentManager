package ua.abond.lab4.web.validation;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.service.Validator;
import ua.abond.lab4.web.dto.ChangePasswordDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ChangePasswordDTOValidator implements Validator<ChangePasswordDTO> {

    @Override
    public List<String> validate(ChangePasswordDTO object) {
        List<String> errors = new ArrayList<>();
        String oldPassword = object.getOldPassword();
        if (Objects.isNull(oldPassword) || "".equals(oldPassword)) {
            errors.add("account.password.error.null");
        }
        String newPassword = object.getNewPassword();
        if (Objects.isNull(newPassword) || "".equals(newPassword)) {
            errors.add("account.password.new.error.null");
        }
        String newPasswordCopy = object.getNewPasswordCopy();
        if (!Objects.isNull(newPassword) && !newPassword.equals(newPasswordCopy)) {
            errors.add("account.password.new.error.not.equal");
        }
        return errors;
    }
}
