package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.Order;
import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;

import java.util.List;

public class OrderValidator implements Validator<Order> {
    @Override
    public List<String> validate(Order object) {
        return Validation.<Order, String>reader().map(v ->
                v.validate(Order::getDuration, duration -> duration > 0, "Duration should be larger than zero")
        ).run(object).getExceptions();
    }
}
