package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.dao.ApartmentDAO;

@Component
public class ApartmentServiceImpl {
    private final ApartmentDAO apartmentDAO;

    @Inject
    public ApartmentServiceImpl(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }
}
