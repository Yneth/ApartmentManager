package ua.abond.lab4.core.jdbc;

import javax.sql.DataSource;

public class SimpleJdbcTemplate extends JdbcTemplate {
    private static final Object EMPTY = new Object();
    private static final ThreadLocal<Object> managed = new ThreadLocal<>();

    public SimpleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void beginTransaction() {
        managed.set(EMPTY);
    }

    @Override
    public void endTransaction() {
        managed.remove();
    }

    @Override
    public boolean isManaged() {
        return managed.get() != null;
    }
}
