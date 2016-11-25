package ua.abond.lab4.service.impl;

import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void createOrder(Order order) {
        orderDAO.create(order);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderDAO.getById(id);
    }

    @Override
    public void confirmOrder(Order order) {

    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return null;
    }
}
