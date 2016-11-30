package ua.abond.lab4.dao;

import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;

import java.util.List;

public interface ApartmentDAO extends DAO<Apartment> {
    List<Apartment> list(Pageable pageable);

    List<Apartment> list(Pageable pageable, Apartment filter);
}
