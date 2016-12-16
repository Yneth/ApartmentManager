package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.annotation.Prop;
import ua.abond.lab4.core.annotation.Value;
import ua.abond.lab4.core.web.support.DefaultPage;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.core.jdbc.JdbcTemplate;
import ua.abond.lab4.core.jdbc.KeyHolder;
import ua.abond.lab4.core.jdbc.PreparedStatementSetter;
import ua.abond.lab4.core.jdbc.RowMapper;
import ua.abond.lab4.core.jdbc.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Prop("sql/user.sql.properties")
public class JdbcUserDAO extends JdbcDAO<User> implements UserDAO {
    @Value("sql.create")
    private String createSql;
    @Value("sql.update")
    private String updateSql;
    @Value("sql.deleteById")
    private String deleteByIdSql;
    @Value("sql.getById")
    private String getByIdSql;
    @Value("sql.getByLogin")
    private String getByLoginSql;
    @Value("sql.list")
    private String listSql;
    @Value("sql.count")
    private String countSql;

    @Inject
    public JdbcUserDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(User entity) {
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    createSql,
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
        return jdbcTemplate.querySingle(getByIdSql,
                ps -> ps.setLong(1, id),
                new UserMapper()
        );
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.execute(updateSql,
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
        jdbcTemplate.execute(deleteByIdSql,
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return jdbcTemplate.querySingle(getByLoginSql,
                ps -> ps.setString(1, login),
                new UserMapper()
        );
    }

    @Override
    public Page<User> list(Pageable pageable, Long authId) {
        PreparedStatementSetter pss = ps -> ps.setLong(1, authId);

        long count = jdbcTemplate.querySingle(countSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));
        List<User> query = jdbcTemplate.query(
                String.format(listSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new UserMapper()
        );
        return new DefaultPage<>(query, count, pageable);
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
