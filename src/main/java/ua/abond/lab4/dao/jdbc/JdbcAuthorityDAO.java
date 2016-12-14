package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.Prop;
import ua.abond.lab4.config.core.annotation.Value;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.util.jdbc.JdbcTemplate;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@Prop("sql/authority.sql.properties")
public class JdbcAuthorityDAO extends JdbcDAO<Authority>
        implements AuthorityDAO {

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

    @Inject
    public JdbcAuthorityDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Authority entity) {
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    createSql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getName());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Authority> getById(Long id) {
        return jdbcTemplate.querySingle(getByIdSql,
                ps -> ps.setLong(1, id),
                new AuthorityMapper()
        );
    }

    @Override
    public void update(Authority entity) {
        jdbcTemplate.execute(updateSql,
                ps -> {
                    ps.setString(1, entity.getName());
                    ps.setLong(2, entity.getId());
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
    public Optional<Authority> getByName(String name) {
        return jdbcTemplate.querySingle(getByNameSql,
                ps -> ps.setString(1, name),
                new AuthorityMapper()
        );
    }

    private static class AuthorityMapper implements RowMapper<Authority> {

        @Override
        public Authority mapRow(ResultSet rs) throws SQLException {
            Long entityId = rs.getLong(1);
            String name = rs.getString(2);
            Authority authority = new Authority();

            authority.setId(entityId);
            authority.setName(name);
            return authority;
        }
    }
}
