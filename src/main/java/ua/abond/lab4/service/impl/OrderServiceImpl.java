package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;

import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    @Inject
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
    public void confirmOrder(Long id, int price) {

    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDAO.getUserOrders(userId);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public List<Order> list(Pageable pageable) {
        return orderDAO.list(pageable);
    }
}
