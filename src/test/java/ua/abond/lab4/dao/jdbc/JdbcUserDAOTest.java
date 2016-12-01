package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.UserDAO;

public class JdbcUserDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;

    public JdbcUserDAOTest() {
        super("users-dataset.xml");
    }

    @Before
    public void setUp() {
        userDAO = new JdbcUserDAO(dataSource);
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