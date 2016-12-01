package ua.abond.lab4.service.impl;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.RequestService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RequestServiceImplIntegrationTest {
    private static final String DATASET = "orders-dataset.xml";
    private static final String TEST_PACKAGE = "ua.abond.lab4.service.impl";

    private IDatabaseTester tester;
    private RequestDAO requestDAO;
    private UserDAO userDAO;
    private ApartmentDAO apartmentDAO;
    private RequestService requestService;

    @Before
    public void setUp() throws Exception {
        BeanFactory bf = new AnnotationBeanFactory(TEST_PACKAGE);
        requestDAO = bf.getBean(RequestDAO.class);
        requestService = bf.getBean(RequestService.class);
        userDAO = bf.getBean(UserDAO.class);
        apartmentDAO = bf.getBean(ApartmentDAO.class);
        tester = bf.getBean(IDatabaseTester.class);
        tester.setDataSet(new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(DATASET)
        ));
        tester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        tester.onSetup();
    }

    @Test
    public void createRequest() throws Exception {
        Request request = new Request();
        request.setUser(userDAO.getById(0L).get());
        request.setLookup(apartmentDAO.getById(0L).get());
        request.setFrom(LocalDateTime.now().minusDays(10));
        request.setTo(LocalDateTime.now());
        requestService.createRequest(request);

        assertNotNull(request.getId());
        Optional<Request> byId = requestDAO.getById(request.getId());
        assertFalse(Optional.empty().equals(byId));
    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void confirmRequest() throws Exception {

    }

    @Test
    public void getUserRequests() throws Exception {

    }

    @Test
    public void rejectRequest() throws Exception {

    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void listCreated() throws Exception {

    }

}