package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;

import javax.sql.DataSource;

public class JdbcApartmentDAO extends JdbcDAO<Apartment>
        implements ApartmentDAO {

    public JdbcApartmentDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Apartment entity) {

    }

    @Override
    public Apartment getById(Long id) {
        return null;
    }

    @Override
    public void update(Apartment entity) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
