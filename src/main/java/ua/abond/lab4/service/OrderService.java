package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

public interface OrderService {
    Order createOrder(ConfirmRequestDTO requestDTO) throws ServiceException;

    Order getById(Long id) throws ServiceException;

    void payOrder(Long id) throws ServiceException;

    void deleteOrder(Order order);

    Page<Order> list(Pageable pageable);

    Page<Order> getUserOrders(Pageable pageable, Long id);
}
