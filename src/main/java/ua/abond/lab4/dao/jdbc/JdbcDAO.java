package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.DAO;
import ua.abond.lab4.util.jdbc.Jdbc;

import javax.sql.DataSource;

public abstract class JdbcDAO<T> implements DAO<T> {
    protected final Jdbc jdbc;

    public JdbcDAO(DataSource dataSource) {
        this.jdbc = new Jdbc(dataSource);
    }

    public void rollback() {
        jdbc.rollback();
    }

    public void commit() {
        jdbc.commit();
    }

    public void beginTransaction() {
        jdbc.beginTransaction();
    }

    public void endTransaction() {
        jdbc.endTransaction();
    }

}
