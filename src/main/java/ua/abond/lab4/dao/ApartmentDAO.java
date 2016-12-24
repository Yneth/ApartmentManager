package ua.abond.lab4.dao;

import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ApartmentDAO extends DAO<Apartment> {
    Page<Apartment> list(Pageable pageable);

    Page<Apartment> list(Pageable pageable, Request filter);

    Page<Apartment> listFree(Pageable pageable, LocalDate from, LocalDate to);

    long count();
}
