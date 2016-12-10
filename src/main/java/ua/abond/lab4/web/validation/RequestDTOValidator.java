package ua.abond.lab4.web.validation;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.service.Validator;
import ua.abond.lab4.web.dto.RequestDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RequestDTOValidator implements Validator<RequestDTO> {

    @Override
    public List<String> validate(RequestDTO object) {
        List<String> errors = new ArrayList<>();
        LocalDateTime from = object.getFrom();
        if (Objects.isNull(from)) {
            errors.add("request.validation.from.null");
        }
        LocalDateTime to = object.getTo();
        if (Objects.isNull(to)) {
            errors.add("request.validation.to.null");
        }
        Long apartmentTypeId = object.getApartmentTypeId();
        if (Objects.isNull(apartmentTypeId)) {
            errors.add("request.validation.type.null");
        }
        int roomCount = object.getRoomCount();
        if (roomCount > 11) {
            errors.add("request.validation.room.count.greater");
        }
        if (roomCount <= 0) {
            errors.add("request.validation.room.count.less");
        }
        if (from != null && to != null && from.isAfter(to)) {
            errors.add("request.validation.from.after.to");
        }
        return errors;
    }
}
