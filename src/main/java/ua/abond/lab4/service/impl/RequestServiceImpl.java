package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.annotation.Transactional;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.*;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.RejectRequestException;
import ua.abond.lab4.service.exception.RequestConfirmException;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.dto.RequestDTO;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
public class RequestServiceImpl implements RequestService {
    private static final Logger logger = Logger.getLogger(RequestServiceImpl.class);

    private final OrderDAO orderDAO;
    private final RequestDAO requestDAO;
    private final ApartmentDAO apartmentDAO;

    @Inject
    public RequestServiceImpl(OrderDAO orderDAO, RequestDAO requestDAO, ApartmentDAO apartmentDAO) {
        this.orderDAO = orderDAO;
        this.requestDAO = requestDAO;
        this.apartmentDAO = apartmentDAO;
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

        dto.setId(request.getId());
    }

    @Override
    @Transactional
    public void confirmRequest(ConfirmRequestDTO requestDTO)
            throws ServiceException {
        logger.debug(String.format("Confirming request with id: %s", requestDTO.getRequestId()));
        Request request = requestDAO.getById(requestDTO.getRequestId()).
                orElseThrow(ResourceNotFoundException::new);
        Apartment apartment = apartmentDAO.getById(requestDTO.getApartmentId()).
                orElseThrow(ResourceNotFoundException::new);

        if (RequestStatus.CREATED != request.getStatus()) {
            throw new RequestConfirmException(String.format("Request with id %d is already confirmed or rejected",
                    request.getId()
            ));
        }

        request.setStatus(RequestStatus.CONFIRMED);
        requestDAO.update(request);

        long dayCount = ChronoUnit.DAYS.between(request.getFrom(), request.getTo());
        BigDecimal price = apartment.getPrice().multiply(new BigDecimal(dayCount));
        requestDTO.setPrice(price);

        orderDAO.create(new Order.Builder().buildFrom(requestDTO));
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

    @Override
    public void deleteById(Long id) {
        requestDAO.deleteById(id);
    }
}
