package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.*;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.RequestDTO;
import ua.abond.lab4.web.dto.UserSessionDTO;
import ua.abond.lab4.web.mapper.PageableRequestMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    public static final String APARTMENTS_VIEW = "/WEB-INF/pages/user/apartments.jsp";
    public static final String ORDER_VIEW = "/WEB-INF/pages/user/order.jsp";
    public static final String ORDERS_VIEW = "/WEB-INF/pages/user/orders.jsp";
    public static final String REQUEST_VIEW = "/WEB-INF/pages/user/request.jsp";
    public static final String REQUESTS_VIEW = "/WEB-INF/pages/user/requests.jsp";
    public static final String REQUEST_CREATE_VIEW = "/WEB-INF/pages/user/create-request.jsp";

    @Inject
    private ValidationService validationService;
    @Inject
    private RequestMapperService mapperService;
    @Inject
    private UserService userService;
    @Inject
    private RequestService requestService;
    @Inject
    private OrderService orderService;
    @Inject
    private ApartmentService apartmentService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);
        Pageable pageable = mapperService.map(req, Pageable.class);

        Page<Request> userRequests = requestService.getUserRequests(pageable, user.getId());
        req.setAttribute("page", userRequests);

        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }

    @RequestMapping("/request")
    public void viewRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        Request request = requestService.getById(id);
        req.setAttribute("request", request);
        req.getRequestDispatcher(REQUEST_VIEW).forward(req, resp);
    }

    @RequestMapping("/request/new")
    public void getCreateRequestPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        List<ApartmentType> list = apartmentTypeDAO.list();
        req.setAttribute("apartmentTypes", list);
        req.getRequestDispatcher(REQUEST_CREATE_VIEW).forward(req, resp);
    }

    @OnException("/user/request/new")
    @RequestMapping(value = "/request/new", method = RequestMethod.POST)
    public void createRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);
        RequestDTO request = mapperService.map(req, RequestDTO.class);

        req.setAttribute("request", request);
        validationService.validate(request);

        request.setUserId(user.getId());
        requestService.createRequest(request);
        resp.sendRedirect("/user/requests");
    }

    @OnException(value = "/user/request")
    @RequestMapping(value = "/request/reject", method = RequestMethod.POST)
    public void rejectRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        String comment = req.getParameter("comment");
        requestService.rejectRequest(id, comment);
        resp.sendRedirect("/user/requests");
    }

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);
        Pageable pageable = new PageableRequestMapper().map(req);

        Page<Order> page = orderService.getUserOrders(pageable, user.getId());
        req.setAttribute("page", page);

        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }

    @RequestMapping("/order")
    public void viewOrder(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        Order order = orderService.getById(id);
        req.setAttribute("order", order);
        req.getRequestDispatcher(ORDER_VIEW).forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void viewApartments(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm");
        LocalDateTime from = Parse.localDateTime(req.getParameter("from"), dtf);
        LocalDateTime to = Parse.localDateTime(req.getParameter("to"), dtf);

        req.setAttribute("from", from);
        req.setAttribute("to", to);

        if (Objects.isNull(from) || Objects.isNull(to)) {
            req.setAttribute("page", apartmentService.list(pageable));
        } else {
            req.setAttribute("page", apartmentService.listFree(pageable, from, to));
        }
        req.getRequestDispatcher(APARTMENTS_VIEW).forward(req, resp);
    }
}
