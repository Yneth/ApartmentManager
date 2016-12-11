package ua.abond.lab4.dao.jdbc;

import org.dbunit.dataset.ITable;
import org.junit.Test;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcRequestDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;
    private RequestDAO requestDAO;
    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("requests.xml");
        userDAO = beanFactory.getBean(UserDAO.class);
        requestDAO = beanFactory.getBean(RequestDAO.class);
        apartmentDAO = beanFactory.getBean(ApartmentDAO.class);
        apartmentTypeDAO = beanFactory.getBean(ApartmentTypeDAO.class);
    }

    @Test
    public void create() throws Exception {
        Request request = new Request();
        Apartment apartment = new Apartment();
        apartment.setRoomCount(12);
        apartment.setType(apartmentTypeDAO.getById(0L).get());

        request.setLookup(apartment);
        request.setUser(userDAO.getById(0L).get());
        request.setFrom(LocalDateTime.now().minusDays(10));
        request.setTo(LocalDateTime.now());
        request.setStatus(RequestStatus.CREATED);
        requestDAO.create(request);

        assertNotNull(request.getId());
        Request actual = requestDAO.getById(request.getId()).get();
        assertTrue(request.getLookup().equals(actual.getLookup()));
        assertTrue(request.getUser().getId().equals(actual.getUser().getId()));
    }

    @Test
    public void testGetById() throws Exception {
        Optional<Request> byId = requestDAO.getById(0L);
        assertNotEquals(Optional.empty(), byId);
        ITable orders = dataSet.getTable("requests");

        String id = (String) orders.getValue(0, "id");
        assertEquals(new Long(Long.parseLong(id)), byId.get().getId());
    }

    @Test
    public void testGetByIdNonExisting() throws Exception {
        Optional<Request> byId = requestDAO.getById(-10L);
        assertEquals(Optional.empty(), byId);
    }

    @Test
    public void update() throws Exception {
        Request request = requestDAO.getById(0L).get();
        request.setTo(LocalDateTime.now());
        requestDAO.update(request);

        assertTrue(request.getTo().equals(requestDAO.getById(0L).get().getTo()));
    }

    @Test
    public void deleteById() throws Exception {
        requestDAO.deleteById(0L);
        assertTrue(Optional.empty().equals(requestDAO.getById(0L)));
    }

    @Test
    public void testList() throws Exception {
        Page<Request> list = requestDAO.list(new DefaultPageable(1, 1, SortOrder.ASC));
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

        Page<Request> page = requestDAO.list(new DefaultPageable(1, 1, SortOrder.ASC));
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertFalse(page.hasContent());
    }

    @Test
    public void testGetUserRequests() throws Exception {
        Page<Request> page = requestDAO.getUserOrders(new DefaultPageable(1, 1, SortOrder.ASC), 1L);
        assertNotNull(page);
        assertTrue(page.hasContent());
        assertNotNull(page.getContent());
        assertEquals(1, page.getSize());
        assertEquals(1, page.getTotalPages());
    }
}