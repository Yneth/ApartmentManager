package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.annotation.Prop;
import ua.abond.lab4.core.annotation.Value;
import ua.abond.lab4.core.web.support.DefaultPage;
import ua.abond.lab4.core.web.support.DefaultPageable;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.*;
import ua.abond.lab4.core.jdbc.JdbcTemplate;
import ua.abond.lab4.core.jdbc.KeyHolder;
import ua.abond.lab4.core.jdbc.PreparedStatementSetter;
import ua.abond.lab4.core.jdbc.RowMapper;
import ua.abond.lab4.core.jdbc.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
@Prop("sql/request.sql.properties")
public class JdbcRequestDAO extends JdbcDAO<Request> implements RequestDAO {
    @Value("sql.create")
    private String createSql;
    @Value("sql.update")
    private String updateSql;
    @Value("sql.deleteById")
    private String deleteByIdSql;
    @Value("sql.getById")
    private String getByIdSql;
    @Value("sql.list")
    private String listSql;
    @Value("sql.count")
    private String countSql;
    @Value("sql.user.orders")
    private String userOrdersSql;
    @Value("sql.user.orders.count")
    private String countUserOrdersSql;

    @Inject
    public JdbcRequestDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Request entity) {
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(createSql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, entity.getUser().getId());
            ps.setInt(2, entity.getLookup().getRoomCount());
            ps.setLong(3, entity.getLookup().getType().getId());
            ps.setObject(4, Timestamp.valueOf(entity.getFrom()));
            ps.setObject(5, Timestamp.valueOf(entity.getTo()));
            ps.setLong(6, entity.getStatus().ordinal());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Request> getById(Long id) {
        return jdbcTemplate.querySingle(
                getByIdSql,
                ps -> ps.setLong(1, id),
                new RequestMapper()
        );
    }

    @Override
    public void update(Request entity) {
        jdbcTemplate.execute(updateSql,
                ps -> {
                    ps.setLong(1, entity.getUser().getId());
                    ps.setInt(2, entity.getLookup().getRoomCount());
                    ps.setLong(3, entity.getLookup().getType().getId());
                    ps.setObject(4, Timestamp.valueOf(entity.getFrom()));
                    ps.setObject(5, Timestamp.valueOf(entity.getTo()));
                    ps.setObject(6, entity.getStatus().ordinal());
                    ps.setObject(7, entity.getStatusComment());
                    ps.setObject(8, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.execute(deleteByIdSql,
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Page<Request> list(Pageable pageable) {
        List<Request> query = jdbcTemplate.query(
                String.format(listSql, pageable.getPageSize(), pageable.getOffset()),
                new RequestMapper()
        );
        return new DefaultPage<>(query, count(), pageable);
    }

    @Override
    public Page<Request> getUserOrders(Pageable pageable, Long userId) {
        PreparedStatementSetter pss = ps -> ps.setLong(1, userId);
        long count = jdbcTemplate.querySingle(countUserOrdersSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));

        List<Request> query = jdbcTemplate.query(
                String.format(userOrdersSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new RequestMapper()
        );
        return new DefaultPage<>(query, count, new DefaultPageable(1, 10, null));
    }

    @Override
    public long count() {
        return jdbcTemplate.querySingle(countSql, rs -> rs.getLong(1)).
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
            request.setFrom(rs.getObject(6, Timestamp.class).toLocalDateTime());
            request.setTo(rs.getObject(7, Timestamp.class).toLocalDateTime());
            request.setStatus(RequestStatus.values()[rs.getInt(8)]);
            request.setStatusComment(rs.getString(9));
            return request;
        }
    }
}
