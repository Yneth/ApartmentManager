package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Objects;

@Component
public class ApartmentServiceImpl implements ApartmentService {
    private static final Logger logger = Logger.getLogger(ApartmentServiceImpl.class);

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

        Apartment toUpdate = apartmentDAO.getById(apartment.getId()).
                orElseThrow(ResourceNotFoundException::new);
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
        logger.debug("Getting a list of apartments.");
        return apartmentDAO.list(pageable);
    }

    @Override
    public Page<Apartment> listMostAppropriate(Pageable pageable, Request filter) {
        logger.debug("Getting a filtered list");
        return apartmentDAO.list(pageable, filter);
    }

    @Override
    public Apartment getById(Long id) throws ServiceException {
        return apartmentDAO.getById(id).
                orElseThrow(ResourceNotFoundException::new);
    }
}
