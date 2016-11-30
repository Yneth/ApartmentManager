package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.Order;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

public class OrderRequestMapper implements RequestMapper<Order> {

    @Override
    public Order map(HttpServletRequest req) {
        Order order = new Order();
        order.setDuration(Parse.integer(req.getParameter("duration")));
        order.setLookup(new ApartmentRequestMapper().map(req));
        return order;
    }
}
