package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.RequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

@Component
public class RequestDTORequestMapper implements RequestMapper<RequestDTO> {

    @Override
    public RequestDTO map(HttpServletRequest req) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(Parse.longValue(req.getParameter("id")));
        requestDTO.setApartmentTypeId(Parse.longValue(req.getParameter("apartmentTypeId")));
        requestDTO.setRoomCount(Parse.integerPrimitive(req.getParameter("roomCount")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm");
        requestDTO.setFrom(Parse.localDateTime(req.getParameter("from"), formatter));
        requestDTO.setTo(Parse.localDateTime(req.getParameter("to"), formatter));

        requestDTO.setStatusComment(req.getParameter("statusComment"));
        return requestDTO;
    }
}
