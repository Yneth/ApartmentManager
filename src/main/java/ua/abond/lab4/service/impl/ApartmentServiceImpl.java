package ua.abond.lab4.service.impl;

import ua.abond.lab4.dao.ApartmentDAO;

public class ApartmentServiceImpl {
    private final ApartmentDAO apartmentDAO;

    public ApartmentServiceImpl(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }
}
