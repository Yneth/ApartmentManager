package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.Order;

import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class JdbcOrderDAOTest extends JdbcDAOTest {
    private OrderDAO orderDAO;
    private ApartmentDAO apartmentDAO;
    private RequestDAO requestDAO;

    public JdbcOrderDAOTest() {
        super("orders-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        orderDAO = new JdbcOrderDAO(dataSource);
        apartmentDAO = new JdbcApartmentDAO(dataSource);
        requestDAO = new JdbcRequestDAO(dataSource);
    }

    @Test
    public void create() throws Exception {
        Order order = new Order();
        order.setPayed(false);
        order.setPrice(new BigDecimal(10));
        order.setRequest(requestDAO.getById(0L).get());
        order.setApartment(apartmentDAO.getById(0L).get());
        orderDAO.create(order);
        assertNotNull(order.getId());
        assertNotNull(orderDAO.getById(order.getId()).get());
    }

    @Test
    public void getById() throws Exception {
        Optional<Order> byId = orderDAO.getById(0L);
        assertNotEquals(Optional.empty(), byId);
        Order order = byId.get();
        assertTrue(new BigDecimal(100).equals(order.getPrice()));
        assertFalse(order.isPayed());
        assertNotNull(order.getApartment());
        assertNotNull(order.getApartment().getId());
        assertNotNull(order.getRequest());
        assertNotNull(order.getRequest().getId());
    }

    @Test
    public void update() throws Exception {
        Order byId = orderDAO.getById(0L).get();
        byId.setPayed(true);
        byId.setPrice(new BigDecimal(200));
        byId.setApartment(apartmentDAO.getById(1L).get());
        byId.setRequest(requestDAO.getById(0L).get());

        orderDAO.update(byId);
        Order actual = orderDAO.getById(0L).get();
        assertEquals(byId.getPrice(), actual.getPrice());
        assertEquals(byId.isPayed(), actual.isPayed());
        assertEquals(byId.getApartment().getId(), actual.getApartment().getId());
        assertEquals(byId.getRequest().getId(), actual.getRequest().getId());
    }

    @Test
    public void deleteById() throws Exception {
        orderDAO.deleteById(0L);
        assertTrue(Optional.empty().equals(orderDAO.getById(0L)));
    }
}