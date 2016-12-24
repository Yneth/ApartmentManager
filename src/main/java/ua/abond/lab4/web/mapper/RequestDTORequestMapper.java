package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.RequestDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestDTORequestMapper implements RequestMapper<RequestDTO> {

    @Override
    public RequestDTO map(HttpServletRequest req) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(Parse.longObject(req.getParameter("id")));
        requestDTO.setApartmentTypeId(Parse.longObject(req.getParameter("apartmentTypeId")));
        requestDTO.setRoomCount(Parse.intValue(req.getParameter("roomCount")));

        requestDTO.setFrom(Parse.localDate(req.getParameter("from")));
        requestDTO.setTo(Parse.localDate(req.getParameter("to")));

        requestDTO.setStatusComment(req.getParameter("statusComment"));
        return requestDTO;
    }
}
