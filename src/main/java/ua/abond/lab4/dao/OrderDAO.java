package ua.abond.lab4.dao;

import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Order;

import java.util.Optional;

public interface OrderDAO extends DAO<Order> {
    Optional<Order> findByRequestId(Long requestId);

    long count();

    Page<Order> list(Pageable pageable);

    Page<Order> getUserOrders(Pageable pageable, Long id);
}
