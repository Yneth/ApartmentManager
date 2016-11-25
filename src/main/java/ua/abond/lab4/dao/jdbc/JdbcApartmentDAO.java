package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.util.jdbc.Jdbc;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcApartmentDAO extends JdbcDAO<Apartment>
        implements ApartmentDAO {

    public JdbcApartmentDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Apartment entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO apartment(id, room_count, type_id) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setInt(2, entity.getRoomCount());
            ps.setLong(3, entity.getType().getId());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Apartment> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT id, room_count, type_id, a.name FROM apartments " +
                        "INNER JOIN apartment_types a ON a.type_id = type_id " +
                        "WHERE id = ?;",
                ps -> ps.setLong(1, id),
                new ApartmentMapper()
        );
    }

    @Override
    public void update(Apartment entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE apartments SET room_count = ? type_id = ? WHERE id = ?",
                ps -> {
                    ps.setInt(1, entity.getRoomCount());
                    ps.setLong(2, entity.getType().getId());
                    ps.setLong(3, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("DELETE FROM apartments WHERE id = ?",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public List<Apartment> filterBy(Apartment apartment) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.query("SELECT id, room_count, type_id, a.name FROM apartments " +
                        "INNER JOIN apartment_types a ON a.type_id = type_id " +
                        "WHERE room_count = ? AND a.name = ?;",
                ps -> {
                    ps.setInt(1, apartment.getRoomCount());
                    ps.setString(2, apartment.getType().getName());
                },
                new ApartmentMapper()
        );
    }

    private static class ApartmentMapper implements RowMapper<Apartment> {

        @Override
        public Apartment mapRow(ResultSet rs) throws SQLException {
            Apartment apartment = new Apartment();

            apartment.setId(rs.getLong(1));
            apartment.setRoomCount(rs.getInt(2));
            ApartmentType type = new ApartmentType();
            type.setId(rs.getLong(3));
            type.setName(rs.getString(4));
            apartment.setType(type);
            return apartment;
        }
    }
}
