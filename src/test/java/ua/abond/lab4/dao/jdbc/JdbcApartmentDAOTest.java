package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.dao.ApartmentDAO;

public class JdbcApartmentDAOTest {
    private ApartmentDAO apartmentDAO;

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("apartments-test");
        dataSource.setUser("postgres");
        dataSource.setPassword("123");
        dataSource.setCurrentSchema("public");

        apartmentDAO = new JdbcApartmentDAO(dataSource);
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

}