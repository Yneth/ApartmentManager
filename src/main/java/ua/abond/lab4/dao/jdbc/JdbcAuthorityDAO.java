package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;

import javax.sql.DataSource;

public class JdbcAuthorityDAO extends JdbcDAO<Authority>
        implements AuthorityDAO {

    public JdbcAuthorityDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Authority entity) {
        Jdbc jdbc = new Jdbc();
        jdbc.query("SELECT id, name FROM Authority WHERE name = ?",
                ps -> ps.setString(1, entity.getName()),
                
                );
    }

    @Override
    public Authority getById(Long id) {
        return null;
    }

    @Override
    public void update(Authority entity) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
