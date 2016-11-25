package ua.abond.lab4.service;

import ua.abond.lab4.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void createOrder(Order order);
    Optional<Order> getById(Long id);
    void confirmOrder(Order order);

    List<Order> getUserOrders(Long userId);
}
