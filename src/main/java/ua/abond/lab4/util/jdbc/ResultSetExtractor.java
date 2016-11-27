package ua.abond.lab4.util.jdbc;

import ua.abond.lab4.util.jdbc.exception.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {
    T extract(ResultSet rs) throws SQLException, DataAccessException;
}
