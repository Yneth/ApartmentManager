package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void deleteOrder(Order order);

    void confirmRequest(ConfirmRequestDTO requestDTO) throws ServiceException;

    List<Order> list(Pageable pageable);

    Optional<Order> getById(Long id);
}
