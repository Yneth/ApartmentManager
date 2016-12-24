package ua.abond.lab4.service;

import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ApartmentService {
    Apartment getById(Long id) throws ServiceException;

    void createApartment(Apartment apartment) throws ServiceException;

    void updateApartment(Apartment apartment) throws ServiceException;

    void deleteApartment(Long id);

    Page<Apartment> list(Pageable pageable);

    Page<Apartment> listFree(Pageable pageable, LocalDate from, LocalDate to);

    Page<Apartment> listMostAppropriate(Pageable pageable, Request filter);
}
