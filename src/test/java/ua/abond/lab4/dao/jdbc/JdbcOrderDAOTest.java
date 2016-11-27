package ua.abond.lab4.dao.jdbc;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Order;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class JdbcOrderDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    public JdbcOrderDAOTest() {
        super("orders-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userDAO = new JdbcUserDAO(dataSource);
        orderDAO = new JdbcOrderDAO(dataSource);
        apartmentDAO = new JdbcApartmentDAO(dataSource);
        apartmentTypeDAO = new JdbcApartmentTypeDAO(dataSource);
    }

    @Test
    public void create() throws Exception {
        Order order = new Order();
        Apartment apartment = new Apartment(12, apartmentTypeDAO.getById(0L).get());
        order.setLookup(apartment);
        order.setUser(userDAO.getById(0L).get());
        orderDAO.create(order);

        assertNotNull(order.getId());
        Order actual = orderDAO.getById(order.getId()).get();
        assertTrue(order.getLookup().equals(actual.getLookup()));
        assertTrue(order.getUser().getId().equals(actual.getUser().getId()));
        assertEquals(order.getDuration(), actual.getDuration());
    }

    @Test
    public void testGetById() throws Exception {
        Optional<Order> byId = orderDAO.getById(0L);
        assertNotEquals(Optional.empty(), byId);
        ITable orders = dataSet.getTable("orders");

        String id = (String) orders.getValue(0, "id");
        assertEquals(new Long(Long.parseLong(id)), byId.get().getId());
    }

    @Test
    public void testGetByIdNonExisting() throws Exception {
        Optional<Order> byId = orderDAO.getById(-10L);
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }
}