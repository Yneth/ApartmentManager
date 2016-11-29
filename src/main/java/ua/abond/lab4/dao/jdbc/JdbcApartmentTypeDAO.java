package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.dao.ApartmentTypeDAO;
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
public class JdbcApartmentTypeDAO implements ApartmentTypeDAO {
    private final Jdbc jdbc;

    @Inject
    public JdbcApartmentTypeDAO(DataSource dataSource) {
        this.jdbc = new Jdbc(dataSource);
    }

    @Override
    public void create(ApartmentType entity) {
        KeyHolder keyHolder = new KeyHolder();
        final String sql = "INSERT INTO apartment_types(id, name) VALUES (DEFAULT, ?);";
        jdbc.update(
                conn -> {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, entity.getName());
                    return ps;
                }, keyHolder
        );
    }

    @Override
    public Optional<ApartmentType> getById(Long id) {
        return jdbc.querySingle("SELECT id, name FROM apartment_types WHERE id = ?",
                ps -> ps.setLong(1, id),
                new ApartmentTypeRowMapper()
        );
    }

    @Override
    public void update(ApartmentType entity) {
        jdbc.execute("UPDATE apartment_types SET name = ?",
                ps -> ps.setString(1, entity.getName())
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.execute("DELETE FROM apartment_types WHERE id = ?", ps -> ps.setLong(1, id));
    }

    @Override
    public Optional<ApartmentType> getByName(String name) {
        return jdbc.querySingle("SELECT id, name FROM apartment_types WHERE name = ?",
                ps -> ps.setString(1, name),
                new ApartmentTypeRowMapper()
        );
    }

    @Override
    public List<ApartmentType> list() {
        return jdbc.query("SELECT id, name FROM apartment_types", new ApartmentTypeRowMapper());
    }

    private static class ApartmentTypeRowMapper implements RowMapper<ApartmentType> {
        @Override
        public ApartmentType mapRow(ResultSet rs) throws SQLException {
            ApartmentType apartmentType = new ApartmentType();
            apartmentType.setId(rs.getLong(1));
            apartmentType.setName(rs.getString(2));
            return apartmentType;
        }
    }
}
