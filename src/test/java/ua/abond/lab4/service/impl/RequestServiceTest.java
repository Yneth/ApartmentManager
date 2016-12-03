package ua.abond.lab4.service.impl;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class RequestServiceTest {
    private static final String DATASET = "orders-dataset.xml";
    private static final String TEST_PACKAGE = "ua.abond.lab4.db";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private IDatabaseTester tester;
    private RequestDAO requestDAO;
    private UserDAO userDAO;
    private ApartmentDAO apartmentDAO;
    private RequestService requestService;
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        BeanFactory bf = new AnnotationBeanFactory(TEST_PACKAGE);
        requestDAO = bf.getBean(RequestDAO.class);
        requestService = bf.getBean(RequestService.class);
        userDAO = bf.getBean(UserDAO.class);
        apartmentDAO = bf.getBean(ApartmentDAO.class);
        orderService = bf.getBean(OrderService.class);
        tester = bf.getBean(IDatabaseTester.class);
        tester.setDataSet(new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(DATASET)
        ));
        tester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        tester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
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
    public void testSuccessfulConfirmRequest() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        requestDTO.setApartmentId(0L);
        requestService.confirmRequest(requestDTO);
    }

    @Test(expected = ServiceException.class)
    public void testConfirmNonExistingRequest() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(-1L);
        requestService.confirmRequest(requestDTO);
    }

    @Test(expected = ServiceException.class)
    public void testConfirmRejectedRequest() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(1L);
        requestService.confirmRequest(requestDTO);
    }

    @Test(expected = ServiceException.class)
    public void testConfirmConfirmedRequest() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(2L);
        requestService.confirmRequest(requestDTO);
    }

    @Test(expected = ServiceException.class)
    public void testRejectNullRequest() throws Exception {
        requestService.rejectRequest(-1L, null);
    }

    @Test(expected = ServiceException.class)
    public void testRejectConfirmedRequest() throws Exception {
        Request request = requestDAO.getById(1L).orElse(null);
        requestService.rejectRequest(request.getId(), "Failed to approve");
    }

    @Test(expected = ServiceException.class)
    public void testRejectRejectedRequest() throws Exception {
        Request request = requestDAO.getById(2L).orElse(null);
        requestService.rejectRequest(request.getId(), "Failed to approve");
    }

    @Test
    public void testSuccessfulRequestRejection() throws Exception {
        requestService.rejectRequest(0L, "Changed date.");
        Request request = requestService.getById(0L).orElse(null);
        assertNotNull(request);
        assertTrue(RequestStatus.REJECTED == request.getStatus());
    }

    @Test
    public void testRejectAfterSuccessfulRejection() throws Exception {
        requestService.rejectRequest(0L, null);
        exception.expect(ServiceException.class);
        requestService.rejectRequest(0L, null);
    }

    @Test
    public void testRejectAfterConfirmation() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        requestDTO.setApartmentId(0L);
        requestService.confirmRequest(requestDTO);

        exception.expect(ServiceException.class);
        requestService.rejectRequest(requestDTO.getRequestId(), null);
    }

    @Test
    public void testOrderCountOnBadRequestUpdate() throws Exception {
        RequestDAO requestDAO = mock(RequestDAO.class);
        doThrow(new DataAccessException()).when(requestDAO).update(any());
        RequestService requestService = new RequestServiceImpl(orderService, mock(RequestDAO.class));

        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        requestDTO.setApartmentId(0L);
        try {
            requestService.confirmRequest(requestDTO);
        } catch (ServiceException e) {
        }
        assertEquals(2, orderService.list(new DefaultPageable(1, 10, null, null)).getSize());
    }

    @Test
    public void testRequestStatusOnBadOrderSave() throws Exception {
        OrderService orderService = mock(OrderService.class);
        doThrow(new DataAccessException()).when(orderService).createOrder(any());
        RequestService requestService = new RequestServiceImpl(orderService, requestDAO);

        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        requestDTO.setApartmentId(0L);
        try {
            requestService.confirmRequest(requestDTO);
        } catch (ServiceException e) {
        }
        assertTrue(RequestStatus.CREATED == requestDAO.getById(0L).orElse(null).getStatus());
    }
}