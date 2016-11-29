package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            List<Order> userOrders = orderService.getUserOrders(user.getId());
            req.setAttribute("orders", userOrders);
        }
        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @RequestMapping("/order")
    public void viewOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        orderService.getById(id).ifPresent(order -> {
            req.setAttribute("order", order);
        });
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @RequestMapping("/order/new")
    public void getCreateOrderPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ApartmentType> list = apartmentTypeDAO.list();
        req.setAttribute("apartmentTypes", list);
        req.getRequestDispatcher("create-order.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/order/new", method = RequestMethod.POST)
    public void createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Order order = parseOrder(req);
        User user = (User) req.getSession(false).getAttribute("user");
        order.setUser(user);
        orderService.createOrder(order);
        resp.sendRedirect("/user/orders");
    }

    @RequestMapping(value = "/order/delete", method = RequestMethod.POST)
    public void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("/user/orders");
    }

    private Order parseOrder(HttpServletRequest req) {
        Order order = new Order();
        order.setDuration(Integer.parseInt(req.getParameter("duration")));
        Apartment apartment = new Apartment();
        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(Long.parseLong(req.getParameter("apartmentTypeId")));
        apartment.setRoomCount(Integer.parseInt(req.getParameter("roomCount")));
        apartment.setType(apartmentType);
        order.setLookup(apartment);
        order.setDuration(Integer.parseInt(req.getParameter("duration")));
        return order;
    }
}
