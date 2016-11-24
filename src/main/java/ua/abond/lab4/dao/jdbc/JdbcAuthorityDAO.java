package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

public class JdbcAuthorityDAO extends JdbcDAO<Authority>
        implements AuthorityDAO {

    public JdbcAuthorityDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Authority entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        IdHolder holder = new GeneratedIdHolder();
        jdbc.execute(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO Authority(id, name) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(2, entity.getName());
        }, holder);
        entity.setId(holder.getId());
    }

    @Override
    public Authority getById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.query("SELECT id, name FROM Authority WHERE name = ?",
                ps -> ps.setString(1, entity.getName()),
                rs -> {
                    Long entityId = rs.getLong(1);
                    String name = rs.getName(2);
                    Authority authority = new Authority();

                    authority.setId(entityId);
                    authority.setName(name);
                    return authority;
                }
        );
    }

    @Override
    public void update(Authority entity) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("UPDATE Authority SET name = ? WHERE id = ?",
                ps -> {
                    ps.setString(1, entity.getName());
                    ps.setLong(2, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        Jdbc jdbc = new Jdbc(dataSource);
        jdbc.execute("DELETE FROM Authority WHERE id = ?",
                ps -> {
                    ps.setLong(1, entity.getId());
                }
        );
    }
}
