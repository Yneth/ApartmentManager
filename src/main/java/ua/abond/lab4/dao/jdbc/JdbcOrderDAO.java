package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.jdbc.Jdbc;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

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
        final String sql = "INSERT INTO orders (id, user_id, room_count, apartment_type_id, duration) " +
                "VALUES (DEFAULT, ?, ?, ?, ?);";

        Jdbc jdbc = new Jdbc(dataSource);
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, entity.getUser().getId());
            ps.setInt(2, entity.getLookup().getRoomCount());
            ps.setLong(3, entity.getLookup().getType().getId());
            ps.setInt(4, entity.getDuration());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Order> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT o.id, o.user_id, o.room_count, o.apartment_type_id, at.name, o.duration " +
                        "FROM orders o " +
                        "INNER JOIN apartment_types at ON at.id = o.apartment_type_id " +
                        "WHERE o.id = ?;",
                ps -> ps.setLong(1, id),
                new OrderMapper()
        );
    }

    @Override
    public void update(Order entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE orders SET user_id = ?, room_count = ?, apartment_type_id = ?, duration = ? " +
                        "WHERE id = ?",
                ps -> {
                    ps.setLong(1, entity.getUser().getId());
                    ps.setInt(2, entity.getLookup().getRoomCount());
                    ps.setLong(3, entity.getLookup().getType().getId());
                    ps.setLong(4, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("DELETE FROM orders WHERE id = ?;",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public List<Order> paginate(Pageable pageable) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.query(
                "SELECT o.id, o.user_id, o.room_count, o.apartment_type_id, at.name, o.duration " +
                        "FROM orders o " +
                        "INNER JOIN apartment_types at ON at.id = o.apartment_type_id " +
                        "ORDER BY o.id ASC " +
                        "FETCH FIRST 10 ROWS ONLY",
                new OrderMapper()
        );
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.query("SELECT o.id, o.user_id, o.room_count, o.apartment_type_id, at.name, o.duration " +
                        "FROM orders o " +
                        "INNER JOIN apartment_types at ON at.id = o.apartment_type_id " +
                        "WHERE o.user_id = ?",
                ps -> ps.setLong(1, userId),
                new OrderMapper()
        );
    }

    private static class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong(1));
            order.setUser(new User(rs.getLong(2)));
            ApartmentType type = new ApartmentType(rs.getLong(4), rs.getString(5));
            order.setLookup(new Apartment(rs.getInt(3), type));
            order.setDuration(rs.getInt(6));
            return order;
        }
    }
}
