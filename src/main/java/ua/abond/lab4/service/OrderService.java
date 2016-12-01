package ua.abond.lab4.service;

import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.exception.ServiceException;

public interface OrderService {
    void deleteOrder(Order order);

    void confirmRequest(Order order) throws ServiceException;
}
