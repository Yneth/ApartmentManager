package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.DefaultPage;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.*;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcRequestDAO extends JdbcDAO<Request> implements RequestDAO {

    @Inject
    public JdbcRequestDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Request entity) {
        final String sql =
                "INSERT INTO requests (id, user_id, room_count, apartment_type_id, from_date, to_date, status_id) " +
                        "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, entity.getUser().getId());
            ps.setInt(2, entity.getLookup().getRoomCount());
            ps.setLong(3, entity.getLookup().getType().getId());
            ps.setObject(4, Timestamp.valueOf(entity.getTo()));
            ps.setObject(5, Timestamp.valueOf(entity.getFrom()));
            ps.setLong(6, entity.getStatus().ordinal());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Request> getById(Long id) {
        return jdbc.querySingle(
                "SELECT q.id, q.user_id, q.room_count, q.apartment_type_id, at.name, q.from_date, q.to_date, q.status_id, q.status_comment " +
                        "FROM requests q " +
                        "INNER JOIN apartment_types at ON at.id = q.apartment_type_id " +
                        "WHERE q.id = ?;",
                ps -> ps.setLong(1, id),
                new RequestMapper()
        );
    }

    @Override
    public void update(Request entity) {
        jdbc.execute("UPDATE requests SET user_id = ?, room_count = ?, apartment_type_id = ?, from_date = ?, to_date = ?, status_id = ?" +
                        ", status_comment = ? WHERE id = ?",
                ps -> {
                    ps.setLong(1, entity.getUser().getId());
                    ps.setInt(2, entity.getLookup().getRoomCount());
                    ps.setLong(3, entity.getLookup().getType().getId());
                    ps.setObject(4, Timestamp.valueOf(entity.getTo()));
                    ps.setObject(5, Timestamp.valueOf(entity.getFrom()));
                    ps.setObject(6, entity.getStatus().ordinal());
                    ps.setObject(7, entity.getStatusComment());
                    ps.setObject(8, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.execute("DELETE FROM requests WHERE id = ?;",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Page<Request> list(Pageable pageable) {
        List<Request> query = jdbc.query(
                "SELECT q.id, q.user_id, q.room_count, q.apartment_type_id, at.name, q.from_date, q.to_date, q.status_id, q.status_comment " +
                        "FROM requests q " +
                        "INNER JOIN apartment_types at ON at.id = q.apartment_type_id " +
                        "ORDER BY q.id ASC",
                new RequestMapper()
        );
        return new DefaultPage<>(query, count(), pageable);
    }

    @Override
    public Page<Request> getUserOrders(Pageable pageable, Long userId) {
        List<Request> query = jdbc.query(
                "SELECT q.id, q.user_id, q.room_count, q.apartment_type_id, at.name, q.from_date, q.to_date, q.status_id, q.status_comment " +
                        "FROM requests q " +
                        "INNER JOIN apartment_types at ON at.id = q.apartment_type_id " +
                        "WHERE q.user_id = ?",
                ps -> ps.setLong(1, userId),
                new RequestMapper()
        );
        return new DefaultPage<>(query, count(), new DefaultPageable(1, 10, null, null));
    }

    @Override
    public long count() {
        return jdbc.querySingle("SELECT COUNT(*) FROM requests;", rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));
    }

    private static class RequestMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet rs) throws SQLException {
            Request request = new Request();
            request.setId(rs.getLong(1));
            request.setUser(new User(rs.getLong(2)));
            ApartmentType type = new ApartmentType(rs.getLong(4), rs.getString(5));
            Apartment apartment = new Apartment();
            apartment.setRoomCount(rs.getInt(3));
            apartment.setType(type);
            request.setLookup(apartment);
            request.setTo(rs.getObject(6, Timestamp.class).toLocalDateTime());
            request.setFrom(rs.getObject(7, Timestamp.class).toLocalDateTime());
            request.setStatus(RequestStatus.values()[rs.getInt(8)]);
            request.setStatusComment(rs.getString(9));
            return request;
        }
    }
}
