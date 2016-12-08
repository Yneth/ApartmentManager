package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.util.validation.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApartmentValidator implements Validator<Apartment> {

    @Override
    public List<String> validate(Apartment object) {
        List<String> errors = new ArrayList<>();
        String name = object.getName();
        if (Objects.isNull(name) || "".equals(name)) {
            errors.add("Apartment name cannot be empty.");
        }
        int roomCount = object.getRoomCount();
        if (roomCount <= 0 || roomCount > 11) {
            errors.add("Room count should be in range of (0 - 11]");
        }
        ApartmentType type = object.getType();
        if (Objects.isNull(type) || Objects.isNull(type.getId())) {
            errors.add("You didn't select apartment type.");
        }
        BigDecimal price = object.getPrice();
        if (Objects.isNull(price)) {
            errors.add("You didn't select price.");
        }
        if (!Objects.isNull(price) && price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Apartment price cannot be less than zero.");
        }
        return errors;
    }
}
