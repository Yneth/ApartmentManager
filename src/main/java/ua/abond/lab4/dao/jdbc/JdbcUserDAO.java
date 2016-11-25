package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.jdbc.Jdbc;
import ua.abond.lab4.util.jdbc.KeyHolder;
import ua.abond.lab4.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDAO extends JdbcDAO<User> implements UserDAO {

    public JdbcUserDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(User entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        KeyHolder holder = new KeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users (id, first_name, last_name, login, password) VALUES (DEFAULT, ?, ?, ?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getLogin());
            ps.setString(4, entity.getPassword());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<User> getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT u.id, u.first_name, u.last_name, u.login, u.password, u.authority_id, a.name " +
                        "FROM users u " +
                        "INNER JOIN authorities a ON a.id = authority_id " +
                        "WHERE id = ?;",
                ps -> ps.setLong(1, id),
                new UserMapper()
        );
    }

    @Override
    public void update(User entity) {
        Jdbc jdbc = new Jdbc(dataSource);
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
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("DELETE FROM users WHERE id = ?;",
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Jdbc jdbc = new Jdbc(dataSource);
        return jdbc.querySingle("SELECT u.id, u.first_name, u.last_name, u.login, u.password, u.authority_id, a.name " +
                        "FROM users u " +
                        "INNER JOIN authorities a ON a.id = authority_id " +
                        "WHERE login = ?;",
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
            return user;
        }
    }
}
