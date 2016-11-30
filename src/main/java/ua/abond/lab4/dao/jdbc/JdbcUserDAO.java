package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class JdbcUserDAO extends JdbcDAO<User> implements UserDAO {

    @Inject
    public JdbcUserDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(User entity) {
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users (id, first_name, last_name, login, password, authority_id) VALUES (DEFAULT, ?, ?, ?, ?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getLogin());
            ps.setString(4, entity.getPassword());
            ps.setLong(5, entity.getAuthority().getId());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<User> getById(Long id) {
        return jdbc.querySingle("SELECT u.id, u.first_name, u.last_name, u.login, u.password, u.authority_id, a.name " +
                        "FROM users u " +
                        "INNER JOIN authorities a ON a.id = u.authority_id " +
                        "WHERE u.id = ?;",
                ps -> ps.setLong(1, id),
                new UserMapper()
        );
    }

    @Override
    public void update(User entity) {
        jdbc.execute("UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?;",
                ps -> {
                    ps.setString(1, entity.getFirstName());
                    ps.setString(2, entity.getLastName());
                    ps.setString(3, entity.getPassword());
                    ps.setLong(4, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.execute("DELETE FROM users WHERE id = ?;",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return jdbc.querySingle("SELECT u.id, u.first_name, u.last_name, u.login, u.password, u.authority_id, a.name " +
                        "FROM users u " +
                        "INNER JOIN authorities a ON a.id = u.authority_id " +
                        "WHERE u.login = ?;",
                ps -> ps.setString(1, login),
                new UserMapper()
        );
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            User user = new User();

            user.setId(rs.getLong(1));
            user.setFirstName(rs.getString(2));
            user.setLastName(rs.getString(3));
            user.setLogin(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setAuthority(new AuthorityMapper(5).mapRow(rs));
            return user;
        }
    }

    private static class AuthorityMapper implements RowMapper<Authority> {
        private final int offset;

        public AuthorityMapper(int offset) {
            this.offset = offset;
        }

        @Override
        public Authority mapRow(ResultSet rs) throws SQLException {
            Authority authority = new Authority();
            authority.setId(rs.getLong(offset + 1));
            authority.setName(rs.getString(offset + 2));
            return authority;
        }
    }
}
