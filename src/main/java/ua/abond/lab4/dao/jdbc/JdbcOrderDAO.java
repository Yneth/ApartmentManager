package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.Prop;
import ua.abond.lab4.config.core.annotation.Value;
import ua.abond.lab4.config.core.web.support.DefaultPage;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;

import javax.sql.DataSource;
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
    @Value("sql.list")
    private String listSql;
    @Value("sql.count")
    private String countSql;
    @Value("sql.user.orders")
    private String userOrdersSql;

    @Inject
    public JdbcOrderDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Order entity) {
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
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
        return jdbc.querySingle(
                getByIdSql,
                ps -> ps.setLong(1, id),
                new FetchedOrderMapper()
        );
    }

    @Override
    public void update(Order entity) {
        jdbc.execute(updateSql,
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
        jdbc.execute(deleteByIdSql,
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Page<Order> list(Pageable pageable) {
        List<Order> content = jdbc.query(
                String.format(listSql, pageable.getPageSize(), pageable.getOffset()),
                new OrderMapper()
        );
        return new DefaultPage<>(content, count(), pageable);
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable, Long id) {
        List<Order> content = jdbc.query(
                String.format(userOrdersSql, pageable.getPageSize(), pageable.getOffset()),
                ps -> ps.setLong(1, id),
                new OrderMapper()
        );
        return new DefaultPage<>(content, count(), pageable);
    }

    @Override
    public long count() {
        return jdbc.querySingle(countSql, rs -> rs.getLong(1))
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
