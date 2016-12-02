package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Optional;

@Component
public class RequestServiceImpl implements RequestService {
    private static final Logger logger = Logger.getLogger(RequestServiceImpl.class);

    private final OrderService orderService;
    private final RequestDAO requestDAO;

    @Inject
    public RequestServiceImpl(OrderService orderService, RequestDAO requestDAO) {
        this.orderService = orderService;
        this.requestDAO = requestDAO;
    }

    @Override
    public void createRequest(Request request) {
        requestDAO.create(request);
    }

    @Override
    public Optional<Request> getById(Long id) {
        return requestDAO.getById(id);
    }

    @Override
    public Page<Request> getUserRequests(Pageable pageable, Long userId) {
        return requestDAO.getUserOrders(pageable, userId);
    }

    @Override
    public void rejectRequest(Long id, String comment) throws ServiceException {
        Request request = requestDAO.getById(id).orElse(null);
        if (request == null) {
            throw new ServiceException(String.format("Could not find request with such id: %s", id));
        }
        if (RequestStatus.CONFIRMED == request.getStatus()) {
            throw new ServiceException(String.format("Request with id: %s was already confirmed.", id));
        }
        request.setStatus(RequestStatus.REJECTED);
        request.setStatusComment(comment);
        requestDAO.update(request);
    }

    @Override
    public Page<Request> list(Pageable pageable) {
        return requestDAO.list(pageable);
    }

    @Override
    public Page<Request> listCreated(Pageable pageable) {
        return requestDAO.list(pageable);
    }
}
