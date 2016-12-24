package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.core.web.support.DefaultPageable;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.SortOrder;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcApartmentDAOTest extends JdbcDAOTest {
    private static final String DATA_SET = "apartments.xml";

    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    @Override
    public void onBeforeSetup() throws Exception {
        apartmentDAO = beanFactory.getBean(ApartmentDAO.class);
        apartmentTypeDAO = beanFactory.getBean(ApartmentTypeDAO.class);
        dataSet = loadDataSet(DATA_SET);
    }

    @Test
    public void testCreate() throws Exception {
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
    public void testGetById() throws Exception {
        Optional<Apartment> byId = apartmentDAO.getById(1L);
        assertNotEquals(Optional.empty(), byId);
        Apartment apartment = byId.get();
        assertEquals(2, apartment.getRoomCount());
        assertEquals(new Long(1L), apartment.getType().getId());
        assertEquals("vip", apartment.getType().getName());
    }

    @Test
    public void testGetByIdNonExisting() throws Exception {
        assertEquals(Optional.empty(), apartmentDAO.getById(-100L));
    }

    @Test
    public void testUpdate() throws Exception {
        Apartment apartment = apartmentDAO.getById(1L).get();
        apartment.setRoomCount(20);
        apartment.setType(apartmentTypeDAO.getById(0L).get());
        apartmentDAO.update(apartment);

        Apartment updated = apartmentDAO.getById(1L).get();
        assertEquals(apartment.getRoomCount(), updated.getRoomCount());
        assertEquals(apartment.getType(), updated.getType());
    }

    @Test
    public void testDeleteById() throws Exception {
        assertNotEquals(Optional.empty(), apartmentDAO.getById(100L));
        apartmentDAO.deleteById(100L);
        assertEquals(Optional.empty(), apartmentDAO.getById(100L));
    }

    @Test
    public void testDeleteByIdNonExisting() throws Exception {
        apartmentDAO.deleteById(-100000L);
    }

    @Test
    public void testCount() {
        assertEquals(7, apartmentDAO.count());
    }

    @Test
    public void testListOrderedById() {
        Page<Apartment> page = apartmentDAO.list(new DefaultPageable(1, 10, SortOrder.ASC));
        List<Apartment> content = page.getContent();

        for (int i = 0; i < content.size() - 1; i++) {
            Apartment current = content.get(i);
            Apartment next = content.get(i + 1);
            assertTrue(current.getId() < next.getId());
        }
    }

    @Test
    public void testListFiltered() {
        Request request = new Request();
        Apartment lookup = new Apartment();
        lookup.setRoomCount(4);
        lookup.setType(apartmentTypeDAO.getByName("business").get());
        request.setLookup(lookup);
        request.setTo(LocalDate.now().minusYears(10));
        request.setFrom(LocalDate.now().minusYears(10));

        Page<Apartment> page = apartmentDAO.list(new DefaultPageable(1, 10, SortOrder.ASC), request);
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertEquals(5, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    public void testListFilteredPageByOne() {
        Request request = new Request();
        Apartment lookup = new Apartment();
        lookup.setRoomCount(4);
        lookup.setType(apartmentTypeDAO.getByName("business").get());
        request.setLookup(lookup);
        request.setTo(LocalDate.now().minusYears(10));
        request.setFrom(LocalDate.now().minusYears(10));

        Page<Apartment> page = apartmentDAO.list(new DefaultPageable(1, 1, SortOrder.ASC), request);
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertEquals(5, page.getTotalElements());
        assertEquals(5, page.getTotalPages());
    }
}