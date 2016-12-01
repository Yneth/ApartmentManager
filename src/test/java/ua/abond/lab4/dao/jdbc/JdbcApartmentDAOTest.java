package ua.abond.lab4.dao.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;

import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JdbcApartmentDAOTest extends JdbcDAOTest {
    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    public JdbcApartmentDAOTest() {
        super("apartments-dataset.xml");
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        apartmentDAO = new JdbcApartmentDAO(dataSource);
        apartmentTypeDAO = new JdbcApartmentTypeDAO(dataSource);
    }

    @Test
    public void create() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setType(apartmentTypeDAO.getByName("vip").get());
        apartment.setRoomCount(10);
        apartment.setPrice(new BigDecimal(10));
        apartmentDAO.create(apartment);

        assertNotNull(apartment.getId());
        Optional<Apartment> byId = apartmentDAO.getById(apartment.getId());
        assertNotEquals(Optional.empty(), byId);
        assertEquals(apartment.getId(), byId.get().getId());
        assertEquals(apartment.getRoomCount(), byId.get().getRoomCount());
        assertTrue(new BigDecimal(10).equals(apartment.getPrice()));
    }

    @Test
    public void getById() throws Exception {
        Optional<Apartment> byId = apartmentDAO.getById(1L);
        assertNotEquals(Optional.empty(), byId);
        Apartment apartment = byId.get();
        assertEquals(2, apartment.getRoomCount());
        assertEquals(new Long(1L), apartment.getType().getId());
        assertEquals("vip", apartment.getType().getName());
    }

    @Test
    public void getByIdNonExisting() throws Exception {
        assertEquals(Optional.empty(), apartmentDAO.getById(-100L));
    }

    @Test
    public void update() throws Exception {
        Apartment apartment = apartmentDAO.getById(1L).get();
        apartment.setRoomCount(20);
        apartment.setType(apartmentTypeDAO.getById(0L).get());
        apartmentDAO.update(apartment);

        Apartment updated = apartmentDAO.getById(1L).get();
        assertEquals(apartment.getRoomCount(), updated.getRoomCount());
        assertEquals(apartment.getType(), updated.getType());
    }

    @Test
    public void deleteById() throws Exception {
        assertNotEquals(Optional.empty(), apartmentDAO.getById(0L));
        apartmentDAO.deleteById(0L);
        assertEquals(Optional.empty(), apartmentDAO.getById(0L));
    }

}