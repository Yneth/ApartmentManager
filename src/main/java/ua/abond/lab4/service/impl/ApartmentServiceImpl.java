package ua.abond.lab4.service.impl;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentDAO apartmentDAO;

    @Inject
    public ApartmentServiceImpl(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }

    @Override
    public void createApartment(Apartment apartment) {
        apartmentDAO.create(apartment);
    }

    @Override
    public void updateApartment(Apartment apartment) throws ServiceException {
        Objects.requireNonNull(apartment.getId());

        Apartment toUpdate = getById(apartment.getId());
        toUpdate.setName(apartment.getName());
        toUpdate.setPrice(apartment.getPrice());
        toUpdate.setType(apartment.getType());

        apartmentDAO.update(apartment);
    }

    @Override
    public void deleteApartment(Long id) {
        apartmentDAO.deleteById(id);
    }

    @Override
    public Page<Apartment> list(Pageable pageable) {
        return apartmentDAO.list(pageable);
    }

    @Override
    public Page<Apartment> listFree(Pageable pageable, LocalDate from, LocalDate to) {
        return apartmentDAO.listFree(pageable, from, to);
    }

    @Override
    public Page<Apartment> listMostAppropriate(Pageable pageable, Request filter) {
        return apartmentDAO.list(pageable, filter);
    }

    @Override
    public Apartment getById(Long id) throws ServiceException {
        return apartmentDAO.getById(id).
                orElseThrow(ResourceNotFoundException::new);
    }
}
