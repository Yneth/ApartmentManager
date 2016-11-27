package ua.abond.lab4.web.user;

import ua.abond.lab4.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderApartment extends HttpServlet {
    private final OrderService orderService;

    public OrderApartment(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
