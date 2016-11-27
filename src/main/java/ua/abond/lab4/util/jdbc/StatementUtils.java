package ua.abond.lab4.util.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public final class StatementUtils {

    private StatementUtils() {
    }

    public static void releaseStatement(Statement statement)
            throws SQLException {
        if (statement == null) {
            return;
        }
        statement.close();
    }
}
