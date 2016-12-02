package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

import java.util.List;

public interface ApartmentService {
    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    void deleteApartment(Long id);

    Page<Apartment> list(Pageable pageable);

    Page<Apartment> listMostAppropriate(Pageable pageable, Request filter);
}
