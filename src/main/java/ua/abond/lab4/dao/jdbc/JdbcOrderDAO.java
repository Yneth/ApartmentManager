package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;

import javax.sql.DataSource;

public class JdbcOrderDAO extends JdbcDAO<Order> implements OrderDAO {

    public JdbcOrderDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
