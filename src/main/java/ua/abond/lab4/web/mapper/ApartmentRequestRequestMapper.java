package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Component
public class ApartmentRequestRequestMapper implements RequestMapper<Request> {

    @Override
    public Request map(HttpServletRequest req) {
        Request request = new Request();

        LocalDate from = Parse.localDate(req.getParameter("from"));
        LocalDate to = Parse.localDate(req.getParameter("to"));

        request.setTo(to);
        request.setFrom(from);
        request.setLookup(new ApartmentRequestMapper().map(req));
        request.setStatus(Parse.enumeration(RequestStatus.class, req.getParameter("status"), null));
        request.setStatusComment(req.getParameter("statusComment"));
        return request;
    }
}
