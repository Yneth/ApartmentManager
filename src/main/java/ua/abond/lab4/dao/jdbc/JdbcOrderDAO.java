package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.DefaultPage;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Apartment;
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
public class JdbcOrderDAO extends JdbcDAO<Order> implements OrderDAO {

    @Inject
    public JdbcOrderDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Order entity) {
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO orders (id, apartment_id, request_id, price, payed) " +
                            "VALUES (DEFAULT, ?, ?, ?, ?);",
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
        return jdbc.querySingle("SELECT o.id, o.apartment_id, o.request_id, o.price, o.payed " +
                        "FROM orders o " +
                        "WHERE o.id = ?;",
                ps -> ps.setLong(1, id),
                new OrderMapper()
        );
    }

    @Override
    public void update(Order entity) {
        jdbc.execute("UPDATE orders SET apartment_id = ?, request_id = ?, price = ?, payed = ? WHERE id = ?;",
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
        jdbc.execute("DELETE FROM orders WHERE id = ?;",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Page<Order> list(Pageable pageable) {
        List<Order> query = jdbc.query(
                "SELECT o.id, o.user_id, o.apartment_id, o.request_id, o.price, o.payed" +
                        "FROM orders o ",
                new OrderMapper()
        );
        return new DefaultPage<>(query, count(), pageable);
    }

    @Override
    public long count() {
        return jdbc.querySingle("SELECT COUNT(*) FROM orders;", rs -> rs.getLong(1))
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
}
