package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.domain.Authority;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class JdbcAuthorityDAOTest {
    private AuthorityDAO authorityDAO;

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("apartments-test");
        dataSource.setUser("postgres");
        dataSource.setPassword("123");
        dataSource.setCurrentSchema("public");

        authorityDAO = new JdbcAuthorityDAO(dataSource);
    }

    @Test
    public void create() throws Exception {
        System.out.println("in");
        Authority authority = new Authority();
        authority.setName("USER");
        authorityDAO.create(authority);

        assertNotNull(authority.getId());
    }

    @Test
    public void getById() throws Exception {
        System.out.println("in");
        Authority authority = new Authority();
        authority.setName("USER");
        authorityDAO.create(authority);

        assertEquals(authority, authorityDAO.getById(authority.getId()).get());
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {
//        authorityDAO.deleteById();
    }
}