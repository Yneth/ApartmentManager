package ua.abond.lab4.service.impl;

import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void createOrder(Order order) {
        orderDAO.create(order);
    }
}
