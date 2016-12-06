package ua.abond.lab4.service.impl;

import org.junit.Test;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.dao.jdbc.JdbcDAOTest;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceImplTest extends JdbcDAOTest {
    private static final String DATA_SET = "orders.xml";

    private UserDAO userDAO;
    private AuthorityDAO authorityDAO;
    private UserService userService;

    @Override
    public void onBeforeSetup() throws Exception {
        dataSet = loadDataSet(DATA_SET);
        userDAO = beanFactory.getBean(UserDAO.class);
        authorityDAO = beanFactory.getBean(AuthorityDAO.class);
        userService = beanFactory.getBean(UserService.class);
    }

    @Test
    public void testSuccessfulRegister() throws Exception {
        String login = "TEST";
        User newUser = createUser(login);

        userService.register(newUser);
        User user = userDAO.getByLogin(login).orElse(null);
        assertNotNull(user);
        assertEquals(login, newUser.getFirstName());
        assertEquals(login, newUser.getLastName());
        Authority authority = authorityDAO.getByName("USER").orElse(null);
        assertEquals(authority, user.getAuthority());
    }

    @Test
    public void testSuccessfulAdminCreation() throws Exception {
        String login = "Test";
        User newUser = createUser(login);

        userService.createAdmin(newUser);
        User user = userDAO.getByLogin(login).orElse(null);
        assertNotNull(user);
        assertEquals(login, newUser.getFirstName());
        assertEquals(login, newUser.getLastName());
        Authority authority = authorityDAO.getByName("ADMIN").orElse(null);
        assertEquals(authority, user.getAuthority());
    }

    @Test(expected = ServiceException.class)
    public void testCreateAdminWhenNoAdminAuthority() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        User user = createUser("test");
        userService.createAdmin(user);
    }

    @Test(expected = ServiceException.class)
    public void testRegisterAlreadyExisting() throws Exception {
        userService.register(createUser("test"));
        userService.register(createUser("test"));
    }

    @Test(expected = ServiceException.class)
    public void testCreateAdminAlreadyExisting() throws Exception {
        userService.createAdmin(createUser("admin"));
        userService.createAdmin(createUser("admin"));
    }

    @Test(expected = ServiceException.class)
    public void testRegisterWhenNoUserAuthority() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        User user = createUser("test");
        userService.register(user);
    }

    @Test(expected = ServiceException.class)
    public void testListAdminsWithNoAdminAuthority() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        userService.listAdmins(new DefaultPageable(1, 10, SortOrder.ASC));
    }

    @Test
    public void updateAccount() throws Exception {
        // TODO
    }

    private User createUser(String login) {
        User newUser = new User();

        newUser.setFirstName(login);
        newUser.setLogin(login);
        newUser.setPassword(login);
        newUser.setLastName(login);
        return newUser;
    }
}