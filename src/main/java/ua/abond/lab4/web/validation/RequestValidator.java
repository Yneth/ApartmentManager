package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.Request;
import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class RequestValidator implements Validator<Request> {
    @Override
    public List<String> validate(Request object) {
        List<String> errors = Validation.<Request, String>reader().map(v ->
                v.validate(Request::getTo, Objects::nonNull, "You didn't select To date.").
                        validate(Request::getFrom, Objects::nonNull, "You didn't select From date").
                        validate(Request::getLookup, Objects::nonNull, "Internal error.").
                        validate(r -> r.getLookup().getType(), Objects::nonNull, "You didn't select apartment type.")
        ).run(object).getExceptions();

        LocalDateTime from = object.getFrom();
        LocalDateTime to = object.getTo();
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
