package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Optional;

public interface RequestService {
    void createRequest(Request request);

    void rejectRequest(Long id, String comment) throws ServiceException;

    Page<Request> getUserRequests(Pageable pageable, Long userId);

    Optional<Request> getById(Long id);

    Page<Request> list(Pageable pageable);

    Page<Request> listCreated(Pageable pageable);
}
