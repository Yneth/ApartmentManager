package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.web.dto.SearchApartmentDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    public static final String HOME_VIEW = "/WEB-INF/pages/index.jsp";
    public static final String APARTMENTS_VIEW = "/WEB-INF/pages/apartments.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ApartmentService apartmentService;

    @RequestMapping("/")
    public void getIndexPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        req.getRequestDispatcher(HOME_VIEW).forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void listApartments(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        SearchApartmentDTO dto = mapperService.map(req, SearchApartmentDTO.class);
        Page<Apartment> apartments;
        if (dto.getCheckIn() != null || dto.getCheckOut() != null) {
            apartments = apartmentService.listFree(pageable, dto.getCheckIn(), dto.getCheckOut());
        } else {
            apartments = apartmentService.list(pageable);
        }
        req.setAttribute("page", apartments);
        req.setAttribute("search", dto);
        req.getRequestDispatcher(APARTMENTS_VIEW).forward(req, resp);
    }
}
