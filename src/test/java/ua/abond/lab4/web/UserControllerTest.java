package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Collections;
import java.util.List;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.isNull;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends ControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private RequestService requestService;
    @Mock
    private ApartmentTypeDAO apartmentTypeDAO;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testViewRequests() throws Exception {
        mockUserToSession(create("login", "password"));
        when(requestService.getUserRequests(or(isNull(), any(Pageable.class)), isNull())).
                thenReturn(mock(Page.class));
        userController.viewRequests(request, response);
        verify(request).getRequestDispatcher(UserController.REQUESTS_VIEW);
        verifyForward();
    }

    @Test
    public void testViewRequest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        Request req = new Request();
        when(requestService.getById(anyLong())).thenReturn(req);

        userController.viewRequest(request, response);

        verify(request).getRequestDispatcher(UserController.REQUEST_VIEW);
        verify(request).setAttribute("request", req);
        verifyForward();
    }

    @Test(expected = ServiceException.class)
    public void testViewRequestWhenNoRequest() throws Exception {
        when(requestService.getById(or(any(Long.class), isNull()))).
                thenThrow(new ServiceException());
        userController.viewRequest(request, response);
    }

    @Test
    public void testGetCreateRequestPage() throws Exception {
        List<ApartmentType> list = Collections.singletonList(new ApartmentType());
        when(apartmentTypeDAO.list()).thenReturn(list);
        userController.getCreateRequestPage(request, response);

        verify(request).setAttribute("apartmentTypes", list);
        verify(request).getRequestDispatcher(UserController.REQUEST_CREATE_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateRequestValidationError() throws Exception {
        userController.getCreateRequestPage(request, response);
        verify(request).setAttribute(anyString(), anyList());
        verify(request).getRequestDispatcher(UserController.REQUEST_CREATE_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateRequest() throws Exception {
        when(request.getParameter("from")).thenReturn("2014-10-20T12:00");
        when(request.getParameter("to")).thenReturn("2015-10-20T12:00");
        when(request.getParameter("status")).thenReturn("0");
        when(request.getParameter("roomCount")).thenReturn("2");
        userController.createRequest(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ServiceException.class)
    public void testRejectRequestWithServiceException() throws Exception {
        doThrow(new ServiceException()).when(requestService).
                rejectRequest(or(any(Long.class), isNull()), or(isNull(), anyString()));
        userController.rejectRequest(request, response);
        verify(request).setAttribute(anyString(), anyList());
        verify(request).getRequestDispatcher(UserController.REQUEST_VIEW);
        verifyForward();
    }

    @Test
    public void testReject() throws Exception {
        userController.rejectRequest(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testViewOrders() throws Exception {
        mockUserToSession(create("login", "password"));
        when(orderService.getUserOrders(or(isNull(), any(Pageable.class)), or(any(Long.class), isNull()))).
                thenReturn(mock(Page.class));

        userController.viewOrders(request, response);
        verify(request).setAttribute(anyString(), or(anyList(), isNull()));
        verify(request).getRequestDispatcher(UserController.ORDERS_VIEW);
        verifyForward();
    }

    @Test
    public void testViewOrder() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        Order order = new Order();
        when(orderService.getById(anyLong())).thenReturn(order);

        userController.viewOrder(request, response);

        verify(request).getRequestDispatcher(UserController.ORDER_VIEW);
        verify(request).setAttribute("order", order);
        verifyForward();
    }

    @Test(expected = ServiceException.class)
    public void testViewOrderWhenNoOrder() throws Exception {
        when(orderService.getById(or(any(Long.class), isNull()))).
                thenThrow(new ServiceException());
        userController.viewOrder(request, response);
    }

    @Test
    public void testPayOrder() throws Exception {
        userController.payOrder(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ServiceException.class)
    public void testPayOrderWithException() throws Exception {
        doThrow(new ServiceException()).when(orderService).
                payOrder(or(isNull(), any(Long.class)));
        userController.payOrder(request, response);
    }
}