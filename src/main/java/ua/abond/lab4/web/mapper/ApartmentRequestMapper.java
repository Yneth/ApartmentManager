package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ApartmentRequestMapper implements RequestMapper<Apartment> {

    @Override
    public Apartment map(HttpServletRequest req) {
        Apartment apartment = new Apartment();
        apartment.setId(Parse.longValue(req.getParameter("id")));
        apartment.setName(req.getParameter("name"));
        apartment.setRoomCount(Parse.integer(req.getParameter("roomCount"), 0));
        apartment.setType(new ApartmentTypeRequestMapper().map(req));
        apartment.setPrice(
                Optional.ofNullable(Parse.doubleValue(req.getParameter("price"))).
                        map(BigDecimal::new).
                        orElse(null)
        );
        return apartment;
    }
}
