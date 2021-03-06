package ua.abond.lab4.core.jdbc.util;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.jdbc.exception.CannotGetConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public final class ConnectionUtils {
    private static final Logger logger = Logger.getLogger(ConnectionUtils.class);

    private ConnectionUtils() {
    }

    public static Connection getConnection(DataSource ds) {
        return getConnection(ds, false);
    }

    public static Connection getConnection(DataSource ds, boolean autoCommit) {
        Objects.requireNonNull(ds);

        try {
            logger.debug("Retrieving connection.");
            Connection connection = ds.getConnection();
            connection.setAutoCommit(autoCommit);
            return connection;
        } catch (SQLException e) {
            throw new CannotGetConnectionException("Could not get connection", e);
        }
    }

    public static void commit(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            logger.error("Failed to invoke commit.", e);
        }
    }

    public static void rollback(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (SQLException e) {
            logger.error("Failed to rollback connection", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            logger.debug("Closing connection.");
            conn.close();
        } catch (SQLException e) {
            logger.error("Failed to rollback connection", e);
        }
    }
}
