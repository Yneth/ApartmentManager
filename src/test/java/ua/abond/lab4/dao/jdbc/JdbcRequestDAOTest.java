package ua.abond.lab4.dao.jdbc;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class JdbcRequestDAOTest extends JdbcDAOTest {
    private UserDAO userDAO;
    private RequestDAO requestDAO;
    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    public JdbcRequestDAOTest() {
        super("requests-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userDAO = new JdbcUserDAO(dataSource);
        requestDAO = new JdbcRequestDAO(dataSource);
        apartmentDAO = new JdbcApartmentDAO(dataSource);
        apartmentTypeDAO = new JdbcApartmentTypeDAO(dataSource);
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

        assertEquals(request, requestDAO.getById(0L).get());
    }

    @Test
    public void deleteById() throws Exception {
        requestDAO.deleteById(0L);
        assertTrue(Optional.empty().equals(requestDAO.getById(0L)));
    }
}