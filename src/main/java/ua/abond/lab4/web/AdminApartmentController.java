package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminApartmentController {
    private static final String APARTMENTS_MAPPING = "/admin/apartments";
    public static final String APARTMENTS_VIEW = "/WEB-INF/pages/admin/apartments.jsp";
    public static final String APARTMENT_VIEW = "/WEB-INF/pages/admin/apartment.jsp";
    public static final String APARTMENT_CREATE_VIEW = "/WEB-INF/pages/admin/create-apartment.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private ApartmentService apartmentService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @RequestMapping("/apartments")
    public void viewApartments(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        Page<Apartment> page = apartmentService.list(pageable);

        req.setAttribute("page", page);
        req.getRequestDispatcher(APARTMENTS_VIEW).forward(req, resp);
    }

    @RequestMapping("/apartment/new")
    public void getApartmentCreatePage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        req.setAttribute("apartmentTypes", apartmentTypeDAO.list());
        req.getRequestDispatcher(APARTMENT_CREATE_VIEW).forward(req, resp);
    }

    @OnException("/admin/apartment/new")
    @RequestMapping(value = "/apartment/new", method = RequestMethod.POST)
    public void createApartment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Apartment apartment = mapperService.map(req, Apartment.class);
        validationService.validate(apartment);

        apartmentService.createApartment(apartment);
        resp.sendRedirect(APARTMENTS_MAPPING);
    }

    @RequestMapping("/apartment")
    public void viewApartment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        req.setAttribute("apartment", apartmentService.getById(id));
        req.setAttribute("apartmentTypes", apartmentTypeDAO.list());
        req.getRequestDispatcher(APARTMENT_VIEW).forward(req, resp);
    }

    @OnException(value = "/admin/apartment")
    @RequestMapping(value = "/apartment/update", method = RequestMethod.POST)
    public void updateApartment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Apartment apartment = mapperService.map(req, Apartment.class);
        validationService.validate(apartment);

        apartmentService.updateApartment(apartment);
        resp.sendRedirect(APARTMENTS_MAPPING);
    }
}
