package ua.abond.lab4.util.jdbc;

import java.sql.Statement;

public interface StatementCallback<T> {
    T run(Statement statement);

    String getSql();
}
