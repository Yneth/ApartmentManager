package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
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

@Component
public class JdbcApartmentDAO extends JdbcDAO<Apartment>
        implements ApartmentDAO {

    @Inject
    public JdbcApartmentDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Apartment entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO apartments (id, room_count, apartment_type_id) VALUES (DEFAULT, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, entity.getRoomCount());
            ps.setLong(2, entity.getType().getId());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Apartment> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT a.id, a.room_count, a.apartment_type_id, at.name " +
                        "FROM apartments a " +
                        "INNER JOIN apartment_types at ON at.id = a.apartment_type_id " +
                        "WHERE a.id = ?;",
                ps -> ps.setLong(1, id),
                new ApartmentMapper()
        );
    }

    @Override
    public void update(Apartment entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE apartments SET room_count = ?, apartment_type_id = ? WHERE id = ?",
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
        return jdbc.query("SELECT a.id, a.room_count, a.apartment_type_id, at.name " +
                        "FROM apartments a " +
                        "INNER JOIN apartment_types a ON at.id = a.apartment_type_id " +
                        "WHERE a.room_count = ? AND at.name = ?;",
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
