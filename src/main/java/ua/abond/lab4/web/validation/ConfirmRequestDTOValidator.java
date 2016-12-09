package ua.abond.lab4.web.validation;

import ua.abond.lab4.util.validation.Validator;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfirmRequestDTOValidator implements Validator<ConfirmRequestDTO> {

    @Override
    public List<String> validate(ConfirmRequestDTO object) {
        List<String> errors = new ArrayList<>();
        Long apartmentId = object.getApartmentId();
        if (Objects.isNull(apartmentId)) {
            errors.add("request.dto.validation.apartment.id.null");
        }
        Long requestId = object.getRequestId();
        if (Objects.isNull(requestId)) {
            errors.add("request.dto.validation.request.id.null");
        }
        Long userId = object.getUserId();
        if (Objects.isNull(userId)) {
            errors.add("request.dto.validation.user.id.null");
        }
        BigDecimal price = object.getPrice();
        if (Objects.isNull(price)) {
            errors.add("request.dto.validation.price.null");
        }
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("request.dto.validation.price");
        }
        return errors;
    }
}
