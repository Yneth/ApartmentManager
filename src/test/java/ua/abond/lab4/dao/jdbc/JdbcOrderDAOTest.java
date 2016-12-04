package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.domain.Order;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcOrderDAOTest extends JdbcDAOTest {
    private OrderDAO orderDAO;
    private ApartmentDAO apartmentDAO;
    private RequestDAO requestDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("orders.xml");
        orderDAO = beanFactory.getBean(OrderDAO.class);
        apartmentDAO = beanFactory.getBean(ApartmentDAO.class);
        requestDAO = beanFactory.getBean(RequestDAO.class);
    }

    @Test
    public void testCreate() throws Exception {
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
    public void testGetById() throws Exception {
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
    public void testGetByIdNonExisting() throws Exception {
        assertNull(orderDAO.getById(-1000L).orElse(null));
    }

    @Test
    public void testUpdate() throws Exception {
        Order byId = orderDAO.getById(0L).orElse(null);
        byId.setPayed(true);
        byId.setPrice(new BigDecimal(200));
        byId.setApartment(apartmentDAO.getById(1L).orElse(null));
        byId.setRequest(requestDAO.getById(0L).orElse(null));

        orderDAO.update(byId);
        Order actual = orderDAO.getById(0L).get();
        assertEquals(byId.getPrice(), actual.getPrice());
        assertEquals(byId.isPayed(), actual.isPayed());
        assertEquals(byId.getApartment().getId(), actual.getApartment().getId());
        assertEquals(byId.getRequest().getId(), actual.getRequest().getId());
    }

    @Test
    public void testDeleteById() throws Exception {
        orderDAO.deleteById(0L);
        assertTrue(Optional.empty().equals(orderDAO.getById(0L)));
    }

    @Test
    public void testCount() throws Exception {
        assertEquals(2L, orderDAO.count());
    }

    @Test
    public void testList() throws Exception {
        Page<Order> list = orderDAO.list(new DefaultPageable(1, 1, "", SortOrder.ASC));
        assertNotNull(list);
        assertNotNull(list.getContent());
        assertEquals(1, list.getSize());
        assertEquals(2, list.getTotalPages());
    }

    @Test
    public void testEmptyList() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        Page<Order> page = orderDAO.list(new DefaultPageable(1, 1, "", SortOrder.ASC));
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertFalse(page.hasContent());
    }

    @Test
    public void testGetUserOrders() throws Exception {
        Page<Order> page = orderDAO.getUserOrders(new DefaultPageable(1, 1, "", SortOrder.ASC), 1L);
        assertNotNull(page);
        assertTrue(page.hasContent());
        assertNotNull(page.getContent());
        assertEquals(1, page.getSize());
        assertEquals(2, page.getTotalPages());
    }
}