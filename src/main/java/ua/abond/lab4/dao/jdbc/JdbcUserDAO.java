package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.User;

import javax.sql.DataSource;

public class JdbcUserDAO extends JdbcDAO<User> implements UserDAO {

    public JdbcUserDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
