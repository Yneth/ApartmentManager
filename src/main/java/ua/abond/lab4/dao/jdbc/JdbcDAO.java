package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;
import ua.abond.lab4.util.jdbc.Jdbc;

import javax.sql.DataSource;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final Jdbc jdbc;

    public JdbcDAO(DataSource dataSource) {
        this.jdbc = new Jdbc(dataSource);
    }
}
