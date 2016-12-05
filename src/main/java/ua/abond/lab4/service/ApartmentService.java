package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.exception.ServiceException;

public interface ApartmentService {
    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment) throws ServiceException;

    void deleteApartment(Long id);

    Page<Apartment> list(Pageable pageable);

    Page<Apartment> listMostAppropriate(Pageable pageable, Request filter);

    Apartment getById(Long id) throws ServiceException;
}
