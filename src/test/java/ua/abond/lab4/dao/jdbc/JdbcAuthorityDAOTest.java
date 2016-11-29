package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JdbcAuthorityDAOTest extends JdbcDAOTest {
    private AuthorityDAO authorityDAO;

    public JdbcAuthorityDAOTest() throws Exception {
        super("authorities-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        authorityDAO = new JdbcAuthorityDAO(dataSource);
    }

    @Test
    public void create() throws Exception {
        Authority authority = new Authority();
        authority.setName("TEST");
        authorityDAO.create(authority);

        assertNotNull(authority.getId());
    }

    @Test
    public void getById() throws Exception {
        Optional<Authority> byId = authorityDAO.getById(1L);
        assertNotEquals(Optional.empty(), byId);
        assertEquals("USER", byId.get().getName());
    }

    @Test
    public void getByName() throws Exception {
        Optional<Authority> byId = authorityDAO.getByName("USER");
        assertNotEquals(Optional.empty(), byId);
        assertEquals("USER", byId.get().getName());
    }

    @Test
    public void getByIdNonExisting() throws Exception {
        Optional<Authority> byId = authorityDAO.getById(100L);
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void getByNameNonExisting() throws Exception {
        Optional<Authority> byId = authorityDAO.getByName("da");
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void update() throws Exception {
        Authority authority = authorityDAO.getById(0L).get();
        authority.setName("Test1");
        authorityDAO.update(authority);

        assertEquals(authority, authorityDAO.getById(0L).get());
    }

    @Test
    public void deleteById() throws Exception {
        Authority authority = new Authority();
        authority.setName("TEST");
        authorityDAO.create(authority);

        authorityDAO.deleteById(authority.getId());

        Optional<Authority> byId = authorityDAO.getById(authority.getId());
        assertEquals(Optional.empty(), byId);
    }
}