package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;
import ua.abond.lab4.util.jdbc.DefaultJdbcTemplate;

import javax.sql.DataSource;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final DefaultJdbcTemplate defaultJdbcTemplate;

    public JdbcDAO(DataSource dataSource) {
        this.defaultJdbcTemplate = new DefaultJdbcTemplate(dataSource);
    }

    public void rollback() {
        defaultJdbcTemplate.rollback();
    }

    public void commit() {
        defaultJdbcTemplate.commit();
    }

    public void beginTransaction() {
        defaultJdbcTemplate.beginTransaction();
    }

    public void endTransaction() {
        defaultJdbcTemplate.endTransaction();
    }

}
