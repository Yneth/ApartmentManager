package ua.abond.lab4.web.validation;

import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ConfirmRequestDTOValidator implements Validator<ConfirmRequestDTO> {

    @Override
    public List<String> validate(ConfirmRequestDTO object) {
        List<String> errors = Validation.<ConfirmRequestDTO, String>reader().map(v ->
                v.validate(ConfirmRequestDTO::getApartmentId, Objects::nonNull, "Apartment id cannot be null.").
                        validate(ConfirmRequestDTO::getRequestId, Objects::nonNull, "Request id cannot be null.").
                        validate(ConfirmRequestDTO::getUserId, Objects::nonNull, "User id cannot be null.").
                        validate(ConfirmRequestDTO::getPrice, Objects::nonNull, "Price cannot be null")
        ).run(object).getExceptions();

        BigDecimal price = object.getPrice();
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Price cannot be less than zero");
        }
        return errors;
    }
}
