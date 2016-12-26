package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.SearchApartmentDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchApartmentDTORequestMapper implements RequestMapper<SearchApartmentDTO> {

    @Override
    public SearchApartmentDTO map(HttpServletRequest req) {
        SearchApartmentDTO dto = new SearchApartmentDTO();
        dto.setRoomCount(Parse.intObject(req.getParameter("roomCount")));
        dto.setCheckIn(Parse.localDate(req.getParameter("from")));
        dto.setCheckOut(Parse.localDate(req.getParameter("to")));
        dto.setLocation(req.getParameter("location"));
        dto.setApartmentTypeIds(
                Optional.ofNullable(req.getParameterValues("roomType")).
                        map(Arrays::stream).
                        map(s -> s.map(Parse::intObject)).
                        map(s -> s.collect(Collectors.toList())).
                        orElse(Collections.emptyList())
        );
        return dto;
    }
}
