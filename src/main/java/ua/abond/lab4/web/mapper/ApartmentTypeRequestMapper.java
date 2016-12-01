package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

public class ApartmentTypeRequestMapper implements RequestMapper<ApartmentType> {

    @Override
    public ApartmentType map(HttpServletRequest req) {
        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(Parse.longValue(req.getParameter("apartmentTypeId")));
        return apartmentType;
    }
}
