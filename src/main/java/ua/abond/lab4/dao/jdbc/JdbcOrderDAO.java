package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.annotation.Prop;
import ua.abond.lab4.core.annotation.Value;
import ua.abond.lab4.core.web.support.DefaultPage;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.core.jdbc.JdbcTemplate;
import ua.abond.lab4.core.jdbc.KeyHolder;
import ua.abond.lab4.core.jdbc.PreparedStatementSetter;
import ua.abond.lab4.core.jdbc.RowMapper;
import ua.abond.lab4.core.jdbc.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Prop("sql/order.sql.properties")
public class JdbcOrderDAO extends JdbcDAO<Order> implements OrderDAO {
    @Value("sql.create")
    private String createSql;
    @Value("sql.update")
    private String updateSql;
    @Value("sql.deleteById")
    private String deleteByIdSql;
    @Value("sql.getById")
    private String getByIdSql;
    @Value("sql.getByRequestId")
    private String getByRequestIdSql;
    @Value("sql.list")
    private String listSql;
    @Value("sql.count")
    private String countSql;
    @Value("sql.user.orders")
    private String userOrdersSql;
    @Value("sql.user.orders.count")
    private String countUserOrdersSql;

    @Inject
    public JdbcOrderDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Order entity) {
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    createSql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, entity.getApartment().getId());
            ps.setLong(2, entity.getRequest().getId());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setBoolean(4, entity.isPayed());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Order> getById(Long id) {
        return jdbcTemplate.querySingle(
                getByIdSql,
                ps -> ps.setLong(1, id),
                new FetchedOrderMapper()
        );
    }

    @Override
    public void update(Order entity) {
        jdbcTemplate.execute(updateSql,
                ps -> {
                    ps.setLong(1, entity.getApartment().getId());
                    ps.setLong(2, entity.getRequest().getId());
                    ps.setBigDecimal(3, entity.getPrice());
                    ps.setBoolean(4, entity.isPayed());
                    ps.setLong(5, entity.getId());
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
    public Page<Order> list(Pageable pageable) {
        List<Order> content = jdbcTemplate.query(
                String.format(listSql, pageable.getPageSize(), pageable.getOffset()),
                rs -> {
                    Order order = new OrderMapper().mapRow(rs);
                    order.getApartment().setName(rs.getString(7));
                    return order;
                }
        );
        return new DefaultPage<>(content, count(), pageable);
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable, Long id) {
        PreparedStatementSetter pss = ps -> ps.setLong(1, id);
        long count = jdbcTemplate.querySingle(countUserOrdersSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));

        List<Order> content = jdbcTemplate.query(
                String.format(userOrdersSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new OrderMapper()
        );
        return new DefaultPage<>(content, count, pageable);
    }

    @Override
    public Optional<Order> findByRequestId(Long requestId) {
        return jdbcTemplate.querySingle(
                getByRequestIdSql,
                ps -> ps.setLong(1, requestId),
                new OrderMapper()
        );
    }

    @Override
    public long count() {
        return jdbcTemplate.querySingle(countSql, rs -> rs.getLong(1))
                .orElseThrow(() -> new DataAccessException("Count cannot be null."));
    }

    private static class OrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong(1));

            Apartment apartment = new Apartment();
            apartment.setId(rs.getLong(2));
            order.setApartment(apartment);

            Request request = new Request();
            request.setId(rs.getLong(3));
            order.setRequest(request);
            order.setPrice(rs.getBigDecimal(4));
            order.setPayed(rs.getBoolean(5));
            return order;
        }
    }

    private static class FetchedOrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs) throws SQLException {
            Order order = new OrderMapper().mapRow(rs);
            Request request = order.getRequest();
            request.setFrom(rs.getTimestamp(6).toLocalDateTime());
            request.setTo(rs.getTimestamp(7).toLocalDateTime());

            Apartment apartment = order.getApartment();
            apartment.setName(rs.getString(8));
            apartment.setRoomCount(rs.getInt(9));

            ApartmentType apartmentType = new ApartmentType();
            apartmentType.setId(rs.getLong(10));
            apartmentType.setName(rs.getString(11));
            apartment.setType(apartmentType);
            return order;
        }
    }
}
