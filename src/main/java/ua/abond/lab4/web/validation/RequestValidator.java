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
            errors.add("You didn't select From date");
        }
        LocalDateTime to = object.getTo();
        if (Objects.isNull(to)) {
            errors.add("You didn't select To date.");
        }
        Apartment lookup = object.getLookup();
        if (Objects.isNull(lookup)) {
            errors.add("Apartment lookup cannot be null.");
        } else {
            ApartmentType apartmentType = object.getLookup().getType();
            if (Objects.isNull(apartmentType)) {
                errors.add("You didn't select apartment type.");
            }
        }
        int roomCount = object.getLookup().getRoomCount();
        if (from != null && to != null) {
            if (from.isAfter(to)) {
                errors.add("To date should be after from date.");
            }
        }
        if (roomCount > 11) {
            errors.add("Room count cannot be larger than 11.");
        }
        if (roomCount <= 0) {
            errors.add("Room count cannot be less or equal to zero.");
        }
        return errors;
    }
}
