package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.exception.RequestConfirmException;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final RequestDAO requestDAO;

    @Inject
    public OrderServiceImpl(OrderDAO orderDAO, RequestDAO requestDAO) {
        this.orderDAO = orderDAO;
        this.requestDAO = requestDAO;
    }

    @Override
    public void deleteOrder(Order order) {
        orderDAO.deleteById(order.getId());
    }

    @Override
    public void confirmRequest(ConfirmRequestDTO requestDTO) throws ServiceException {
        logger.debug(String.format("Confirming request with id: %s", requestDTO.getRequestId()));
        Request request = requestDAO.getById(requestDTO.getRequestId()).orElse(null);
        if (request == null) {
            throw new RequestConfirmException(String.format("Could not find request with such id: %s",
                    requestDTO.getRequestId()));
        }
        try {
            request.setStatus(RequestStatus.CONFIRMED);
            requestDAO.update(request);
            // TODO createOrder
        } catch (DataAccessException e) {
            if (request.getStatus() == RequestStatus.CONFIRMED) {
                request.setStatus(RequestStatus.CREATED);
                requestDAO.update(request);
            }
            // TODO rollback order
            throw new RequestConfirmException(String.format("Failed to confirm request with id: %s",
                    requestDTO.getRequestId()));
        }
    }

    @Override
    public Page<Order> list(Pageable pageable) {
        return orderDAO.list(pageable);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderDAO.getById(id);
    }

    @Override
    public void payOrder(Long id) throws ServiceException {
        Order order = orderDAO.getById(id).orElse(null);
        if (order == null) {
            throw new ServiceException("Could not find such order");
        }
        if (order.isPayed()) {
            throw new ServiceException("Cannot pay already payed order.");
        }
        order.setPayed(true);
        orderDAO.update(order);
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable, Long id) {
        return orderDAO.getUserOrders(pageable, id);
    }
}
