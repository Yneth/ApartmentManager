package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.*;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.RejectRequestException;
import ua.abond.lab4.service.exception.RequestConfirmException;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.dto.RequestDTO;

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
    public void createRequest(RequestDTO dto) {
        Request request = new Request();
        request.setFrom(dto.getFrom());
        request.setTo(dto.getTo());
        request.setStatusComment(dto.getStatusComment());
        request.setStatus(RequestStatus.CREATED);

        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(dto.getApartmentTypeId());
        Apartment apartment = new Apartment();
        apartment.setType(apartmentType);
        apartment.setRoomCount(dto.getRoomCount());
        request.setLookup(apartment);
        User user = new User();
        user.setId(dto.getUserId());
        request.setUser(user);
        requestDAO.create(request);
    }

    @Override
    public void confirmRequest(ConfirmRequestDTO requestDTO)
            throws ServiceException {
        logger.debug(String.format("Confirming request with id: %s", requestDTO.getRequestId()));
        Request request = requestDAO.getById(requestDTO.getRequestId()).orElse(null);
        if (request == null) {
            throw new ResourceNotFoundException();
        }
        if (RequestStatus.CREATED != request.getStatus()) {
            throw new RequestConfirmException(String.format("Request with id %d is already confirmed or rejected",
                    request.getId()
            ));
        }
        Order order = null;
        try {
            request.setStatus(RequestStatus.CONFIRMED);
            requestDAO.update(request);
            order = orderService.createOrder(requestDTO);
        } catch (DataAccessException e) {
            if (request.getStatus() == RequestStatus.CONFIRMED) {
                request.setStatus(RequestStatus.CREATED);
                requestDAO.update(request);
            }
            if (order != null && order.getId() != null) {
                orderService.deleteOrder(order);
            }
            throw new RequestConfirmException(String.format("Failed to confirm request with id: %s",
                    requestDTO.getRequestId()));
        }
    }

    @Override
    public Request getById(Long id) throws ResourceNotFoundException {
        return requestDAO.getById(id).
                orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<Request> getUserRequests(Pageable pageable, Long userId) {
        return requestDAO.getUserOrders(pageable, userId);
    }

    @Override
    public void rejectRequest(Long id, String comment) throws ServiceException {
        Request request = requestDAO.getById(id).orElse(null);
        if (request == null) {
            throw new ResourceNotFoundException();
        }
        if (RequestStatus.CREATED != request.getStatus()) {
            throw new RejectRequestException();
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
