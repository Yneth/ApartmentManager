package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.*;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/supersu")
public class SuperUserController {
    private static final String ADMINS_MAPPING = "/supersu/admins";
    public static final String ORDERS_VIEW = "/WEB-INF/pages/supersu/orders.jsp";
    public static final String REQUESTS_VIEW = "/WEB-INF/pages/supersu/requests.jsp";
    public static final String ADMINS_VIEW = "/WEB-INF/pages/supersu/admins.jsp";
    public static final String CREATE_ADMIN_VIEW = "/WEB-INF/pages/supersu/create-admin.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private RequestService requestService;
    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);

        Page<Order> page = orderService.list(pageable);
        req.setAttribute("page", page);

        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }

    @OnException("/supersu/orders")
    @RequestMapping(value = "/order/delete", method = RequestMethod.POST)
    public void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));

        orderService.deleteOrder(id);
        resp.sendRedirect("/supersu/orders");
    }

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);

        Page<Request> page = requestService.list(pageable);
        req.setAttribute("page", page);

        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }

    @OnException("/supersu/requests")
    @RequestMapping(value = "/request/delete", method = RequestMethod.POST)
    public void deleteRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));

        requestService.deleteById(id);
        resp.sendRedirect("/supersu/requests");
    }

    @OnException(ADMINS_MAPPING)
    @RequestMapping("/admins")
    public void viewAdmins(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);

        Page<User> page = userService.listAdmins(pageable);
        req.setAttribute("page", page);

        req.getRequestDispatcher(ADMINS_VIEW).forward(req, resp);
    }

    @RequestMapping("/admin/new")
    public void getCreateAdminPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        req.getRequestDispatcher(CREATE_ADMIN_VIEW).forward(req, resp);
    }

    @OnException("/supersu/admin/new")
    @RequestMapping(value = "/admin/new", method = RequestMethod.POST)
    public void createAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        User user = mapperService.map(req, User.class);
        validationService.validate(user);

        userService.createAdmin(user);
        resp.sendRedirect("/");
    }

    @OnException(ADMINS_MAPPING)
    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public void deleteAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        userService.deleteAdminById(id);
        resp.sendRedirect(ADMINS_MAPPING);
    }
}

