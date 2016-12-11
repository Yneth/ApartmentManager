package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;
import ua.abond.lab4.util.jdbc.DefaultJdbcTemplate;

import javax.sql.DataSource;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final DefaultJdbcTemplate jdbcTemplate;

    public JdbcDAO(DataSource dataSource) {
        this.jdbcTemplate = new DefaultJdbcTemplate(dataSource);
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
