package ua.abond.lab4.core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void set(PreparedStatement ps) throws SQLException;
}
