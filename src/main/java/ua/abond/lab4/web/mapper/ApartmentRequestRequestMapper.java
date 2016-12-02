package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApartmentRequestRequestMapper implements RequestMapper<Request> {

    @Override
    public Request map(HttpServletRequest req) {
        Request request = new Request();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm");
        LocalDateTime from = Parse.localDateTime(req.getParameter("from"), formatter);
        LocalDateTime to = Parse.localDateTime(req.getParameter("to"), formatter);

        request.setTo(to);
        request.setFrom(from);
        request.setLookup(new ApartmentRequestMapper().map(req));
        request.setStatus(RequestStatus.values()[Parse.integer(req.getParameter("status"), 0)]);
        request.setStatusComment(req.getParameter("statusComment"));
        return request;
    }
}
