package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.dao.UserDAO;

public class JdbcUserDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("users.xml");
        userDAO = beanFactory.getBean(UserDAO.class);
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