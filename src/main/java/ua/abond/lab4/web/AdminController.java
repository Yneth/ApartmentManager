package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.util.OptionalConsumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Inject
    private OrderService orderService;
    @Inject
    private ApartmentService apartmentService;

    @RequestMapping("/order")
    public void getOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        OptionalConsumer.of(orderService.getById(id)).
                ifPresent(order -> {
                    req.setAttribute("order", order);
                }).
                ifNotPresent(() -> {
                    req.setAttribute("errorMessage", "Could not find such order.");
                });
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @RequestMapping("/orders")
    public void getOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void getApartments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", "Not implemented yet.");
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }
}
