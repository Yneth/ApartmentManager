package ua.abond.lab4.service.impl;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;

import java.util.List;

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
    public void updateApartment(Apartment apartment) {
        apartmentDAO.update(apartment);
    }

    @Override
    public void deleteApartment(Long id) {
        apartmentDAO.deleteById(id);
    }

    @Override
    public List<Apartment> list(Pageable pageable) {
        logger.debug("Getting a list of apartments.");
        return apartmentDAO.list(pageable);
    }

    @Override
    public List<Apartment> listMostAppropriate(Pageable pageable, Request filter) {
        return apartmentDAO.list(pageable, filter);
    }
}
