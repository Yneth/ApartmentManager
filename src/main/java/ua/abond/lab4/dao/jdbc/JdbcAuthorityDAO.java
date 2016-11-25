package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.util.jdbc.Jdbc;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcAuthorityDAO extends JdbcDAO<Authority>
        implements AuthorityDAO {

    public JdbcAuthorityDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Authority entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO authorities (id, name) VALUES (DEFAULT, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getName());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Authority> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT id, name FROM authorities WHERE id = ?",
                ps -> ps.setLong(1, id),
                new AuthorityMapper()
        );
    }

    @Override
    public void update(Authority entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE authorities SET name = ? WHERE id = ?",
                ps -> {
                    ps.setString(1, entity.getName());
                    ps.setLong(2, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("DELETE FROM authorities WHERE id = ?",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Optional<Authority> getByName(String name) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT id, name FROM authorities WHERE name = ?",
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
