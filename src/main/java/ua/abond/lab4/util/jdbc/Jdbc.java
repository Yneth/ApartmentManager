package ua.abond.lab4.util.jdbc;

import ua.abond.lab4.util.jdbc.exception.DataAccessException;
import ua.abond.lab4.util.jdbc.util.ConnectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Jdbc {
    private final DataSource dataSource;

    public Jdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int update(PreparedStatementCreator psc, KeyHolder keyHolder) {
        Connection conn = ConnectionUtils.getConnection(dataSource);
        try (PreparedStatement ps = psc.create(conn)) {
            int count = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            while (generatedKeys.next()) {
                keyHolder.setKey(generatedKeys.getLong(1));
            }

            conn.commit();
            return count;
        } catch (SQLException e) {
            ConnectionUtils.rollback(conn);
            throw new DataAccessException("", e);
        } finally {
            ConnectionUtils.closeConnection(conn);
        }
    }

    public void execute(String sql, PreparedStatementSetter setter)
            throws DataAccessException {
        Objects.requireNonNull(sql, "Sql should not be null");

        Connection conn = ConnectionUtils.getConnection(dataSource);
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setter.set(ps);

            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            ConnectionUtils.rollback(conn);
            throw new DataAccessException("", e);
        } finally {
            ConnectionUtils.closeConnection(conn);
        }
    }

    public <T> List<T> query(String sql,
                             PreparedStatementSetter pss,
                             RowMapper<T> rsm)
            throws DataAccessException {
        Objects.requireNonNull(sql, "Sql should not be null");

        List<T> result = null;
        Connection conn = ConnectionUtils.getConnection(dataSource);
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            pss.set(ps);

            List<T> values = new ArrayList<>();
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                values.add(rsm.mapRow(resultSet));
            }
            result = values;
        } catch (SQLException e) {
            ConnectionUtils.rollback(conn);
            throw new DataAccessException("", e);
        } finally {
            ConnectionUtils.closeConnection(conn);
        }
        return result;
    }

    public <T> List<T> query(String sql, RowMapper<T> rsm)
            throws DataAccessException {
        return query(sql, new DefaultPreparedStatementSetter(), rsm);
    }

    public <T> Optional<T> querySingle(String sql,
                                       PreparedStatementSetter pss,
                                       RowMapper<T> rsm)
            throws DataAccessException {
        List<T> query = query(sql, pss, rsm);
        return query.stream().findFirst();
    }

    private static class DefaultPreparedStatementSetter implements PreparedStatementSetter {

        @Override
        public void set(PreparedStatement ps) throws SQLException {

        }
    }
}
