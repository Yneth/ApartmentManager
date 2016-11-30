package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void createOrder(Order order);

    Optional<Order> getById(Long id);

    void confirmOrder(Long id, int price);

    List<Order> getUserOrders(Long userId);

    void deleteOrder(Long id);

    List<Order> list(Pageable pageable);
}
