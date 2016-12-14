package ua.abond.lab4.dao;

import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

public interface ApartmentDAO extends DAO<Apartment> {
    Page<Apartment> list(Pageable pageable);

    Page<Apartment> list(Pageable pageable, Request filter);

    long count();
}
