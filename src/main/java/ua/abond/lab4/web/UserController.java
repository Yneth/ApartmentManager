package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.mapper.ApartmentRequestRequestMapper;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;
import ua.abond.lab4.web.validation.RequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final String ORDER_VIEW = "/WEB-INF/pages/user/order.jsp";
    private static final String ORDERS_VIEW = "/WEB-INF/pages/user/orders.jsp";
    private static final String REQUEST_VIEW = "/WEB-INF/pages/user/request.jsp";
    private static final String REQUESTS_VIEW = "/WEB-INF/pages/user/requests.jsp";
    private static final String REQUEST_CREATE_VIEW = "/WEB-INF/pages/user/create-request.jsp";

    @Inject
    private UserService userService;
    @Inject
    private RequestService requestService;
    @Inject
    private OrderService orderService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        Pageable pageable = new PageableRequestMapper().map(req);

        Page<Request> userRequests = requestService.getUserRequests(pageable, user.getId());
        req.setAttribute("requests", userRequests.getContent());
        req.setAttribute("pageable", pageable);

        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }

    @RequestMapping("/request")
    public void viewRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        requestService.getById(id).ifPresent(request -> {
            req.setAttribute("request", request);
        });
        req.getRequestDispatcher(REQUEST_VIEW).forward(req, resp);
    }

    @RequestMapping("/request/new")
    public void getCreateRequestPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ApartmentType> list = apartmentTypeDAO.list();
        req.setAttribute("apartmentTypes", list);
        req.getRequestDispatcher(REQUEST_CREATE_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/request/new", method = RequestMethod.POST)
    public void createRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);

        Request request = new ApartmentRequestRequestMapper().map(req);
        List<String> errors = new RequestValidator().validate(request);
        if (!errors.isEmpty()) {
            List<ApartmentType> list = apartmentTypeDAO.list();
            req.setAttribute("errors", errors);
            req.setAttribute("request", request);
            getCreateRequestPage(req, resp);
            return;
        }
        request.setUser(user);
        requestService.createRequest(request);
        resp.sendRedirect("/user/requests");
    }

    @RequestMapping(value = "/request/reject", method = RequestMethod.POST)
    public void rejectRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String comment = req.getParameter("comment");
        try {
            requestService.rejectRequest(id, comment);
        } catch (ServiceException e) {
            req.setAttribute("errors", Collections.singletonList(e.getMessage()));
            viewRequest(req, resp);
            return;
        }
        resp.sendRedirect("/user/requests");
    }

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        Pageable pageable = new PageableRequestMapper().map(req);

        Page<Order> page = orderService.getUserOrders(pageable, user.getId());
        req.setAttribute("orders", page.getContent());
        req.setAttribute("pageable", pageable);

        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }

    @RequestMapping("/order")
    public void viewOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Parse.longValue(req.getParameter("id"));
        orderService.getById(id).ifPresent(order -> {
            req.setAttribute("order", order);
        });
        req.getRequestDispatcher(ORDER_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/order/pay", method = RequestMethod.POST)
    public void payOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Parse.longValue(req.getParameter("id"));
        try {
            orderService.payOrder(id);
        } catch (ServiceException e) {
            viewOrder(req, resp);
            return;
        }
        resp.sendRedirect("/orders");
    }
}
