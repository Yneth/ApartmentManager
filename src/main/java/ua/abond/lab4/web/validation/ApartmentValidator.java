package ua.abond.lab4.web.validation;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.service.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ApartmentValidator implements Validator<Apartment> {

    @Override
    public List<String> validate(Apartment object) {
        List<String> errors = new ArrayList<>();
        String name = object.getName();
        if (Objects.isNull(name) || "".equals(name)) {
            errors.add("apartment.validation.name.null");
        }
        int roomCount = object.getRoomCount();
        if (roomCount <= 0 || roomCount > 11) {
            errors.add("apartment.validation.room.count");
        }
        ApartmentType type = object.getType();
        if (Objects.isNull(type) || Objects.isNull(type.getId())) {
            errors.add("apartment.validation.type.null");
        }
        BigDecimal price = object.getPrice();
        if (Objects.isNull(price)) {
            errors.add("apartment.validation.price.null");
        }
        if (!Objects.isNull(price) && price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("apartment.validation.price");
        }
        return errors;
    }
}
