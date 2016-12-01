package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.List;
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
    public List<Request> getUserRequests(Long userId) {
        return requestDAO.getUserOrders(userId);
    }

    @Override
    public void rejectRequest(Long id, String comment) throws ServiceException {
        requestDAO.getById(id).
                map(request -> {
                    request.setStatus(RequestStatus.REJECTED);
                    request.setStatusComment(comment);
                    return request;
                }).
                orElseThrow(() -> new ServiceException(String.format("Could not find request with such id: %s", id)));
    }

    @Override
    public List<Request> list(Pageable pageable) {
        return requestDAO.list(pageable);
    }

    @Override
    public List<Request> listCreated(Pageable pageable) {
        return requestDAO.list(pageable);
    }
}
