package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.annotation.Controller;
import ua.abond.lab4.config.core.web.annotation.OnException;
import ua.abond.lab4.config.core.web.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.mapper.ApartmentRequestMapper;
import ua.abond.lab4.web.mapper.ConfirmRequestDTORequestMapper;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.validation.ApartmentValidator;
import ua.abond.lab4.web.validation.ConfirmRequestDTOValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        ConfirmRequestDTO dto = new ConfirmRequestDTORequestMapper().map(req);
        List<String> errors = new ConfirmRequestDTOValidator().validate(dto);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            viewRequest(req, resp);
            return;
        }

        requestService.confirmRequest(dto);
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/request")
    public void viewRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longValue(req.getParameter("id"));
        Pageable pageable = new PageableRequestMapper().map(req);

        Request request = requestService.getById(id);
        Page<Apartment> apartmentPage = apartmentService.listMostAppropriate(pageable, request);
        req.setAttribute("request", request);
        req.setAttribute("apartments", apartmentPage.getContent());
        req.setAttribute("pageable", pageable);

        req.getRequestDispatcher(REQUEST_VIEW).forward(req, resp);
    }

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = new PageableRequestMapper().map(req);
        Page<Request> page = requestService.list(pageable);

        req.setAttribute("pageable", pageable);
        req.setAttribute("requests", page.getContent());
        req.setAttribute("pageCount", page.getTotalPages());
        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void viewApartments(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = new PageableRequestMapper().map(req);
        Page<Apartment> page = apartmentService.list(pageable);

        req.setAttribute("apartments", page.getContent());
        req.setAttribute("pageable", pageable);
        req.setAttribute("pageCount", page.getTotalPages());
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
        Apartment apartment = new ApartmentRequestMapper().map(req);
        List<String> errors = new ApartmentValidator().validate(apartment);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            getApartmentCreatePage(req, resp);
            return;
        }
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
        Apartment apartment = new ApartmentRequestMapper().map(req);
        List<String> errors = new ApartmentValidator().validate(apartment);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            viewApartment(req, resp);
            return;
        }
        apartmentService.updateApartment(apartment);
        resp.sendRedirect("/admin/apartments");
    }

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = new PageableRequestMapper().map(req);
        Page<Order> page = orderService.list(pageable);

        req.setAttribute("pageable", pageable);
        req.setAttribute("orders", page.getContent());
        req.setAttribute("pageCount", page.getTotalPages());
        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }
}
