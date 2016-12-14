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
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.*;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public static final String REQUEST_VIEW = "/WEB-INF/pages/admin/request.jsp";
    public static final String REQUESTS_VIEW = "/WEB-INF/pages/admin/requests.jsp";
    public static final String ORDERS_VIEW = "/WEB-INF/pages/admin/orders.jsp";
    public static final String APARTMENTS_VIEW = "/WEB-INF/pages/admin/apartments.jsp";
    public static final String APARTMENT_VIEW = "/WEB-INF/pages/admin/apartment.jsp";
    public static final String APARTMENT_CREATE_VIEW = "/WEB-INF/pages/admin/create-apartment.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private OrderService orderService;
    @Inject
    private RequestService requestService;
    @Inject
    private ApartmentService apartmentService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @OnException(value = "/admin/request")
    @RequestMapping(value = "/request/confirm", method = RequestMethod.POST)
    public void confirmRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ConfirmRequestDTO dto = mapperService.map(req, ConfirmRequestDTO.class);
        validationService.validate(dto);

        requestService.confirmRequest(dto);
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/request")
    public void viewRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longValue(req.getParameter("id"));
        Pageable pageable = mapperService.map(req, Pageable.class);

        Request request = requestService.getById(id);
        Page<Apartment> apartmentPage = apartmentService.listMostAppropriate(pageable, request);
        req.setAttribute("request", request);
        req.setAttribute("page", apartmentPage);

        req.getRequestDispatcher(REQUEST_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/request/delete", method = RequestMethod.POST)
    public void deleteRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longValue(req.getParameter("id"));
        requestService.deleteById(id);
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        Page<Request> page = requestService.list(pageable);

        req.setAttribute("page", page);
        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }

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
        resp.sendRedirect("/admin/apartments");
    }

    @RequestMapping("/apartment")
    public void viewApartment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longValue(req.getParameter("id"));
        req.setAttribute("apartment", apartmentService.getById(id));
        req.setAttribute("apartmentTypes", apartmentTypeDAO.list());
        req.getRequestDispatcher(APARTMENT_VIEW).forward(req, resp);
    }

    @OnException(value = "/admin/apartment/order")
    @RequestMapping(value = "/apartment/update", method = RequestMethod.POST)
    public void updateApartment(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Apartment apartment = mapperService.map(req, Apartment.class);
        validationService.validate(apartment);

        apartmentService.updateApartment(apartment);
        resp.sendRedirect("/admin/apartments");
    }

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        Page<Order> page = orderService.list(pageable);

        req.setAttribute("page", page);
        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }
}
