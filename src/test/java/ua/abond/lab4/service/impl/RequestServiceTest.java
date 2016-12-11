package ua.abond.lab4.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.RequestDAO;
import ua.abond.lab4.dao.jdbc.JdbcDAOTest;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.jdbc.exception.DataAccessException;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.dto.RequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class RequestServiceTest extends JdbcDAOTest {
    private static final String DATASET = "orders.xml";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private RequestDAO requestDAO;
    private OrderDAO orderDAO;
    private RequestService requestService;
    private OrderService orderService;

    @Override
    public void onBeforeSetup() throws Exception {
        requestDAO = beanFactory.getBean(RequestDAO.class);
        requestService = beanFactory.getBean(RequestService.class);
        orderDAO = beanFactory.getBean(OrderDAO.class);
        orderService = beanFactory.getBean(OrderService.class);
        dataSet = loadDataSet(DATASET);
    }

    @Test
    public void createRequest() throws Exception {
        RequestDTO request = new RequestDTO();
        request.setUserId(0L);
        request.setApartmentTypeId(0L);
        request.setRoomCount(10);
        request.setFrom(LocalDateTime.now().minusDays(10));
        request.setTo(LocalDateTime.now());
        requestService.createRequest(request);

        assertNotNull(request.getId());
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
        Request request = requestService.getById(0L);
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
        RequestDAO requestDAO = spy(this.requestDAO);
        doThrow(new DataAccessException()).when(requestDAO).update(any());

        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        requestDTO.setApartmentId(0L);
        try {
            requestService.confirmRequest(requestDTO);
        } catch (ServiceException e) {
        }
        assertEquals(2, orderService.list(new DefaultPageable(1, 10, null)).getSize());
    }

    @Test
    public void testRequestStatusOnBadOrderSave() throws Exception {
        ConfirmRequestDTO requestDTO = new ConfirmRequestDTO();
        requestDTO.setRequestId(0L);
        requestDTO.setPrice(new BigDecimal(100));
        try {
            requestService.confirmRequest(requestDTO);
        } catch (Exception e) {
        }
        assertTrue(RequestStatus.CREATED == requestDAO.getById(0L).orElse(null).getStatus());
    }
}