package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.util.OptionalConsumer;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Inject
    private OrderService orderService;
    @Inject
    private ApartmentService apartmentService;

    @RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
    public void confirmOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long orderId = Parse.longInt(req.getParameter("id"));
        Integer price = Parse.integer(req.getParameter("price"));
        orderService.confirmOrder(orderId, price);
        resp.sendRedirect("/admin/orders");
    }

    @RequestMapping("/order")
    public void getOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        OptionalConsumer.of(orderService.getById(id)).
                ifPresent(order -> {
                    req.setAttribute("order", order);
                    req.setAttribute("appropriateApartments", apartmentService.listMostAppropriate(null, order.getLookup()));
                }).
                ifNotPresent(() -> {
                    req.setAttribute("errorMessage", "Could not find such order.");
                });
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @RequestMapping("/orders")
    public void getOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Pageable pageable = new PageableRequestMapper().map(req);
        List<Order> list = orderService.list(pageable);

        req.setAttribute("orders", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void getApartments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Pageable pageable = new PageableRequestMapper().map(req);
        List<Apartment> list = apartmentService.list(pageable);

        req.setAttribute("apartments", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }

    private boolean isAuthorized(User user) {
        return user != null
                && user.getAuthority() != null
                && user.getAuthority().getName() != null
                && user.getAuthority().getName().equalsIgnoreCase("ADMIN");
    }
}
