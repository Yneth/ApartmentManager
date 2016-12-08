package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.Prop;
import ua.abond.lab4.config.core.annotation.Value;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
@Prop("sql/apartment-type.sql.properties")
public class JdbcApartmentTypeDAO extends JdbcDAO<ApartmentType>
        implements ApartmentTypeDAO {

    @Value("sql.create")
    private String createSql;
    @Value("sql.update")
    private String updateSql;
    @Value("sql.deleteById")
    private String deleteByIdSql;
    @Value("sql.getById")
    private String getByIdSql;
    @Value("sql.getByName")
    private String getByNameSql;
    @Value("sql.list")
    private String listSql;

    @Inject
    public JdbcApartmentTypeDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(ApartmentType entity) {
        KeyHolder keyHolder = new KeyHolder();
        jdbc.update(
                conn -> {
                    PreparedStatement ps = conn.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, entity.getName());
                    return ps;
                }, keyHolder
        );
        entity.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Optional<ApartmentType> getById(Long id) {
        return jdbc.querySingle(getByIdSql,
                ps -> ps.setLong(1, id),
                new ApartmentTypeRowMapper()
        );
    }

    @Override
    public void update(ApartmentType entity) {
        jdbc.execute(updateSql,
                ps -> {
                    ps.setString(1, entity.getName());
                    ps.setLong(2, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.execute(deleteByIdSql, ps -> ps.setLong(1, id));
    }

    @Override
    public Optional<ApartmentType> getByName(String name) {
        return jdbc.querySingle(getByNameSql,
                ps -> ps.setString(1, name),
                new ApartmentTypeRowMapper()
        );
    }

    @Override
    public List<ApartmentType> list() {
        return jdbc.query(listSql, new ApartmentTypeRowMapper());
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
