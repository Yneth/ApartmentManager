package ua.abond.lab4.dao;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Order;

public interface OrderDAO extends DAO<Order> {
    Page<Order> list(Pageable pageable);

    long count();
}
