package ua.abond.lab4.dao.jdbc;

import org.junit.Test;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcApartmentTypeDAOTest extends JdbcDAOTest {

    private ApartmentTypeDAO apartmentTypeDAO;

    @Override
    protected void onBeforeSetup() throws Exception {
        dataSet = loadDataSet("apartment-types.xml");
        apartmentTypeDAO = beanFactory.getBean(ApartmentTypeDAO.class);
    }

    @Test
    public void testCreate() throws Exception {
        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setName("TEST");
        apartmentTypeDAO.create(apartmentType);
        assertNotNull(apartmentType.getId());
        assertNotNull(apartmentTypeDAO.getById(apartmentType.getId()).orElse(null));
    }

    @Test
    public void testGetById() throws Exception {
        assertNotNull(apartmentTypeDAO.getById(0L).orElse(null));
    }

    @Test
    public void testGetNonExistingById() throws Exception {
        assertNull(apartmentTypeDAO.getById(-100L).orElse(null));
    }

    @Test
    public void testGetByName() throws Exception {
        assertNotNull(apartmentTypeDAO.getByName("business").orElse(null));
    }

    @Test
    public void testGetNonExistingByName() throws Exception {
        assertNull(apartmentTypeDAO.getByName("---12-1-2").orElse(null));
    }

    @Test
    public void testDelete() throws Exception {
        long id = 0L;
        assertNotNull(apartmentTypeDAO.getById(id).orElse(null));
        apartmentTypeDAO.deleteById(id);
        assertNull(apartmentTypeDAO.getById(id).orElse(null));
    }

    @Test
    public void testUpdate() throws Exception {
        long id = 0L;
        ApartmentType apartmentType = apartmentTypeDAO.getById(id).orElse(null);
        apartmentType.setName("TEST");
        apartmentTypeDAO.update(apartmentType);

        ApartmentType result = apartmentTypeDAO.getById(id).orElse(null);
        assertNotNull(result);
        assertEquals(apartmentType, result);
    }

    @Test
    public void testList() throws Exception {
        List<ApartmentType> list = apartmentTypeDAO.list();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    public void testEmptyList() throws Exception {
        tester.onTearDown();
        tester.setDataSet(loadDataSet("empty.xml"));
        tester.onSetup();

        List<ApartmentType> list = apartmentTypeDAO.list();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        tester.onTearDown();
    }
}