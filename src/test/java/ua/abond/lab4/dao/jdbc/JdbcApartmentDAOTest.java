package ua.abond.lab4.dao.jdbc;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JdbcApartmentDAOTest {
    private static final String DATASET = "orders-dataset.xml";
    private static final String TEST_PACKAGE = "ua.abond.lab4.db";

    private IDatabaseTester tester;
    private ApartmentDAO apartmentDAO;
    private ApartmentTypeDAO apartmentTypeDAO;

    @Before
    public void setUp() throws Exception {
        BeanFactory bf = new AnnotationBeanFactory(TEST_PACKAGE);
        apartmentDAO = bf.getBean(ApartmentDAO.class);
        apartmentTypeDAO = bf.getBean(ApartmentTypeDAO.class);
        tester = bf.getBean(IDatabaseTester.class);
        tester.setDataSet(new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(DATASET)
        ));
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        tester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
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
        assertEquals(2, apartmentDAO.count());
    }

    @Test
    public void testListFiltered() {
        Request request = new Request();
        Apartment lookup = new Apartment();
        lookup.setRoomCount(100);
        lookup.setType(apartmentTypeDAO.getByName("business").get());
        request.setLookup(lookup);
        request.setTo(LocalDateTime.now().minusYears(10));

        Page<Apartment> page = apartmentDAO.list(new DefaultPageable(1, 10, "id", SortOrder.ASC), request);
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertEquals(2, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    public void testListFilteredPageByOne() {
        Request request = new Request();
        Apartment lookup = new Apartment();
        lookup.setRoomCount(100);
        lookup.setType(apartmentTypeDAO.getByName("business").get());
        request.setLookup(lookup);
        request.setTo(LocalDateTime.now().minusYears(10));

        Page<Apartment> page = apartmentDAO.list(new DefaultPageable(1, 1, "id", SortOrder.ASC), request);
        assertNotNull(page);
        assertNotNull(page.getContent());
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
    }
}