package ua.abond.lab4.dao;

import ua.abond.lab4.domain.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order> {
//    List<Order> paginate(Pageable pageable);
    List<Order> getUserOrders(Long id);
}
