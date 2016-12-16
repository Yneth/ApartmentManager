package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public class ConfirmRequestDTORequestMapper implements RequestMapper<ConfirmRequestDTO> {

    @Override
    public ConfirmRequestDTO map(HttpServletRequest req) {
        ConfirmRequestDTO dto = new ConfirmRequestDTO();
        dto.setRequestId(Parse.longObject(req.getParameter("id")));
        dto.setUserId(Parse.longObject(req.getParameter("userId")));
        dto.setApartmentId(Parse.longObject(req.getParameter("apartmentId")));
        return dto;
    }
}
