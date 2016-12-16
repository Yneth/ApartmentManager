package ua.abond.lab4.service;

import ua.abond.lab4.core.annotation.Transactional;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.dto.RequestDTO;

public interface RequestService {
    void createRequest(RequestDTO request) throws ServiceException;

    @Transactional
    void confirmRequest(ConfirmRequestDTO dto) throws ServiceException;

    void rejectRequest(Long id, String comment) throws ServiceException;

    Request getById(Long id) throws ServiceException;

    Page<Request> list(Pageable pageable);

    Page<Request> getUserRequests(Pageable pageable, Long userId);

    Page<Request> listCreated(Pageable pageable);

    void deleteById(Long id);
}
