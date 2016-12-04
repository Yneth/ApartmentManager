package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;

import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcAuthorityDAOTest extends JdbcDAOTest {
    private AuthorityDAO authorityDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("authorities.xml");
        authorityDAO = beanFactory.getBean(AuthorityDAO.class);
    }

    @Test
    public void testCreate() throws Exception {
        Authority authority = new Authority();
        authority.setName("TEST");
        authorityDAO.create(authority);

        assertNotNull(authority.getId());
    }

    @Test
    public void testGetById() throws Exception {
        Optional<Authority> byId = authorityDAO.getById(1L);
        assertNotEquals(Optional.empty(), byId);
        assertEquals("USER", byId.get().getName());
    }

    @Test
    public void testGetByName() throws Exception {
        Optional<Authority> byId = authorityDAO.getByName("USER");
        assertNotEquals(Optional.empty(), byId);
        assertEquals("USER", byId.get().getName());
    }

    @Test
    public void testGetByIdNonExisting() throws Exception {
        Optional<Authority> byId = authorityDAO.getById(100L);
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void testGetByNameNonExisting() throws Exception {
        Optional<Authority> byId = authorityDAO.getByName("da");
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void testUpdate() throws Exception {
        Authority authority = authorityDAO.getById(0L).get();
        authority.setName("Test1");
        authorityDAO.update(authority);

        assertEquals(authority, authorityDAO.getById(0L).get());
    }

    @Test
    public void testDeleteById() throws Exception {
        Authority authority = new Authority();
        authority.setName("TEST");
        authorityDAO.create(authority);

        authorityDAO.deleteById(authority.getId());

        Optional<Authority> byId = authorityDAO.getById(authority.getId());
        assertEquals(Optional.empty(), byId);
    }
}