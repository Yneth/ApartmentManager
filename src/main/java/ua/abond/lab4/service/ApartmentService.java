package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;

import java.util.List;

public interface ApartmentService {
    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    void deleteApartment(Long id);

    List<Apartment> list(Pageable pageable);

    List<Apartment> listMostAppropriate(Pageable pageable, Apartment filter);
}
