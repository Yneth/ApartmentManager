package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.web.mapper.OrderRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserService userService;
    @Inject
    private OrderService orderService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<Order> userOrders = orderService.getUserOrders(user.getId());
        req.setAttribute("orders", userOrders);

        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @RequestMapping("/order")
    public void viewOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        orderService.getById(id).ifPresent(order -> {
            req.setAttribute("order", order);
        });
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @RequestMapping("/order/new")
    public void getCreateOrderPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<ApartmentType> list = apartmentTypeDAO.list();
        req.setAttribute("apartmentTypes", list);
        req.getRequestDispatcher("create-order.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/order/new", method = RequestMethod.POST)
    public void createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Order order = new OrderRequestMapper().map(req);
        order.setUser(user);
        orderService.createOrder(order);
        resp.sendRedirect("/user/orders");
    }

    @RequestMapping(value = "/order/delete", method = RequestMethod.POST)
    public void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        orderService.deleteOrder(id);
        resp.sendRedirect("/user/orders");
    }

    private boolean isAuthorized(User user) {
        return user != null
                && user.getAuthority() != null
                && user.getAuthority().getName() != null
                && user.getAuthority().getName().equalsIgnoreCase("USER");
    }
}
