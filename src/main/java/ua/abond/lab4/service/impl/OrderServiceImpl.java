package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.exception.OrderAlreadyPayedException;
import ua.abond.lab4.service.exception.OrderNotFoundException;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

@Component
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;

    @Inject
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void deleteOrder(Long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public Order createOrder(ConfirmRequestDTO requestDTO) {
        Order order = new Order.Builder().buildFrom(requestDTO);
        orderDAO.create(order);
        return order;
    }

    @Override
    public Order getById(Long id) throws ResourceNotFoundException {
        return orderDAO.getById(id).
                orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void payOrder(Long id) throws ServiceException {
        Order order = orderDAO.getById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        if (order.isPayed()) {
            throw new OrderAlreadyPayedException();
        }
        order.setPayed(true);
        orderDAO.update(order);
    }

    @Override
    public Page<Order> list(Pageable pageable) {
        return orderDAO.list(pageable);
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable, Long id) {
        return orderDAO.getUserOrders(pageable, id);
    }
}
