package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.ApartmentTypeDAO;

public class JdbcApartmentTypeDAOTest extends JdbcDAOTest {
    private ApartmentTypeDAO apartmentTypeDAO;

    public JdbcApartmentTypeDAOTest() {
        super("apartment-types-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        apartmentTypeDAO = new JdbcApartmentTypeDAO(dataSource);
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void getByName() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }
}