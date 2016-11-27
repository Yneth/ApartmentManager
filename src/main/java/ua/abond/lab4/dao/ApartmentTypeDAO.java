package ua.abond.lab4.dao;

import ua.abond.lab4.domain.ApartmentType;

import java.util.Optional;

public interface ApartmentTypeDAO extends DAO<ApartmentType> {
    Optional<ApartmentType> getByName(String name);
}
