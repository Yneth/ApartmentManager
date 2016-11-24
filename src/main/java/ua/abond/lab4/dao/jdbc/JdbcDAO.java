package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;

import javax.sql.DataSource;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final DataSource dataSource;

    public JdbcDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
