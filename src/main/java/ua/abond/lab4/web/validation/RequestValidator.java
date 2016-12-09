package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.util.validation.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestValidator implements Validator<Request> {

    @Override
    public List<String> validate(Request object) {
        List<String> errors = new ArrayList<>();
        LocalDateTime from = object.getFrom();
        if (Objects.isNull(from)) {
            errors.add("request.validation.from.null");
        }
        LocalDateTime to = object.getTo();
        if (Objects.isNull(to)) {
            errors.add("request.validation.to.null");
        }
        Apartment lookup = object.getLookup();
        if (Objects.isNull(lookup)) {
            errors.add("request.validation.lookup.null");
        } else {
            ApartmentType apartmentType = object.getLookup().getType();
            if (Objects.isNull(apartmentType)) {
                errors.add("request.validation.type.null");
            }
            int roomCount = object.getLookup().getRoomCount();
            if (roomCount > 11) {
                errors.add("request.validation.room.count.greater");
            }
            if (roomCount <= 0) {
                errors.add("request.validation.room.count.less");
            }
        }
        if (from != null && to != null) {
            if (from.isAfter(to)) {
                errors.add("request.validation.from.after.to");
            }
        }
        return errors;
    }
}
