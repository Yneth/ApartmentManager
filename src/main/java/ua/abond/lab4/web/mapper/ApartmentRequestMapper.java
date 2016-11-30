package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

public class ApartmentRequestMapper implements RequestMapper<Apartment> {
    @Override
    public Apartment map(HttpServletRequest req) {
        Apartment apartment = new Apartment();
        apartment.setRoomCount(Parse.integer(req.getParameter("roomCount")));
        apartment.setType(new ApartmentTypeRequestMapper().map(req));
        return apartment;
    }
}
