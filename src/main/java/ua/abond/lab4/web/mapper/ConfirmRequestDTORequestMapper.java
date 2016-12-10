package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public class ConfirmRequestDTORequestMapper implements RequestMapper<ConfirmRequestDTO> {

    @Override
    public ConfirmRequestDTO map(HttpServletRequest req) {
        ConfirmRequestDTO dto = new ConfirmRequestDTO();
        dto.setRequestId(Parse.longValue(req.getParameter("id")));
        dto.setUserId(Parse.longValue(req.getParameter("userId")));
        dto.setApartmentId(Parse.longValue(req.getParameter("apartmentId")));
        dto.setPrice(Parse.bigDecimal(req.getParameter("price")));
        return dto;
    }
}
