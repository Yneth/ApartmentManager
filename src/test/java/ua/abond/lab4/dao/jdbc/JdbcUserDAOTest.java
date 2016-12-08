package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;

import static org.junit.Assert.*;

public class JdbcUserDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;
    private AuthorityDAO authorityDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("users.xml");
        userDAO = beanFactory.getBean(UserDAO.class);
        authorityDAO = beanFactory.getBean(AuthorityDAO.class);
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setLogin("TEST");
        user.setPassword("TEST");
        user.setAuthority(authorityDAO.getById(0L).orElse(null));
        userDAO.create(user);
        assertNotNull(user.getId());
        assertNotNull(userDAO.getById(user.getId()).orElse(null));
    }

    @Test
    public void testGetById() throws Exception {
        assertNotNull(userDAO.getById(0L).orElse(null));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userDAO.getById(0L).orElse(null);
        user.setFirstName("TEST");
        userDAO.update(user);
        assertEquals(user.getFirstName(), userDAO.getById(user.getId()).orElse(null).getFirstName());
    }

    @Test
    public void testDeleteById() throws Exception {
        User user = userDAO.getById(0L).orElse(null);
        userDAO.deleteById(user.getId());
        assertNull(userDAO.getById(0L).orElse(null));
    }

    @Test
    public void testGetByLogin() throws Exception {
        User user = userDAO.getByLogin("admin").orElse(null);
        assertNotNull(user);
    }

    @Test
    public void testList() throws Exception {
        Authority authority = authorityDAO.getByName("ADMIN").orElse(null);
        Page<User> list = userDAO.list(new DefaultPageable(1, 1, SortOrder.ASC), authority.getId());
        assertNotNull(list);
        assertNotNull(list.getContent());
        assertEquals(1, list.getSize());
        assertEquals(1, list.getTotalPages());
    }

    @Test
    public void testEmptyList() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        Page<User> page = userDAO.list(new DefaultPageable(1, 1, SortOrder.ASC), 0L);
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertFalse(page.hasContent());
    }
}