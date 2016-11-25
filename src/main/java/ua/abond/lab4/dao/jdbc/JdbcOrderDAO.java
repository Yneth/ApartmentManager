package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.jdbc.Jdbc;
import ua.abond.lab4.util.jdbc.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

public class JdbcOrderDAO extends JdbcDAO<Order> implements OrderDAO {

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
            ps.setInt(4, (int) entity.getDuration().toDays());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Order> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT id, user_id, room_count, apartment_type_id, at.name duration " +
                        "FROM orders " +
                        "INNER JOIN APARTMENT_TYPE at ON at.id = apartment_type_id " +
                        "WHERE id = ?;",
                ps -> ps.setLong(1, id),
                rs -> {
                    Order order = new Order();
                    order.setId(rs.getLong(1));
                    order.setUser(new User(rs.getLong(2)));
                    ApartmentType type = new ApartmentType(rs.getLong(4), rs.getString(5));
                    order.setLookup(new Apartment(rs.getInt(3), type));
                    order.setId(rs.getLong(1));
                    return order;
                }
        );
    }

    @Override
    public void update(Order entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE orders SET user_id = ?, room_count = ?, apartment_type_id = ? WHERE id = ?",
                ps -> {
                    ps.setLong(1, entity.getUser().getId());
                    ps.setInt(2, entity.getLookup().getRoomCount());
                    ps.setLong(3, entity.getLookup().getType().getId());
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
}
