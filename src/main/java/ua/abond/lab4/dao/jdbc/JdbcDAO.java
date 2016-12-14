package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;
import ua.abond.lab4.util.jdbc.JdbcTemplate;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final JdbcTemplate jdbcTemplate;

    public JdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void rollback() {
        jdbcTemplate.rollback();
    }

    public void commit() {
        jdbcTemplate.commit();
    }

    public void beginTransaction() {
        jdbcTemplate.beginTransaction();
    }

    public void endTransaction() {
        jdbcTemplate.endTransaction();
    }

}
