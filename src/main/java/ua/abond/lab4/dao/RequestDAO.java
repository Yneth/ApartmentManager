package ua.abond.lab4.dao;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Request;

public interface RequestDAO extends DAO<Request> {
    Page<Request> list(Pageable pageable);

    Page<Request> getUserOrders(Long id);

    long count();
}
