package ua.abond.lab4.util.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public final class ConnectionUtils {
    private ConnectionUtils() {

    }

    public static Connection getConnection(DataSource ds)
            throws SQLException {
        return getConnection(ds, true);
    }

    public static Connection getConnection(DataSource ds, boolean autoCommit)
            throws SQLException {
        Objects.requireNonNull(ds);

        Connection connection = ds.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public static void closeConnection(Connection conn)
            throws SQLException {
        if (conn == null) {
            return;
        }
        conn.close();
    }
}
