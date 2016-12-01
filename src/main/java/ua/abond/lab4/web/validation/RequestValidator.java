package ua.abond.lab4.web.validation;

import ua.abond.lab4.domain.Request;
import ua.abond.lab4.util.validation.Validation;
import ua.abond.lab4.util.validation.Validator;

import java.util.List;

public class RequestValidator implements Validator<Request> {
    @Override
    public List<String> validate(Request object) {
        return Validation.<Request, String>reader().map(v -> v
//                v.validate(Request::getTo, from -> duration > 0, "Duration should be larger than zero")
        ).run(object).getExceptions();
    }
}
