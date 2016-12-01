package ua.abond.lab4.dao;

import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Request;

import java.util.List;

public interface RequestDAO extends DAO<Request> {
    List<Request> list(Pageable pageable);
    List<Request> getUserOrders(Long id);
}
