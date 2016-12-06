package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest extends ControllerTest {
    @Mock
    private OrderService orderService;
    @Mock
    private RequestService requestService;
    @Mock
    private ApartmentService apartmentService;
    @Mock
    private ApartmentTypeDAO apartmentTypeDAO;
    @InjectMocks
    private AdminController adminController;

    @Test
    public void testConfirmRequestWithNullIdAndValidationError() throws Exception {
        adminController.confirmRequest(request, response);
        verify(request, times(2)).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(AdminController.REQUEST_VIEW);
        verifyForward();
    }

    @Test
    public void testConfirmRequestWithValidationError() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("apartmentId")).thenReturn("1");
        Request request = new Request();
        when(requestService.getById(anyLong())).thenReturn(Optional.of(request));
        when(apartmentService.listMostAppropriate(any(Pageable.class), eq(request))).
                thenReturn(mock(Page.class));
        adminController.confirmRequest(this.request, response);
        verify(this.request, times(1)).setAttribute(eq("errors"), anyList());
        verify(this.request).getRequestDispatcher(AdminController.REQUEST_VIEW);
        verifyForward();
    }

    @Test
    public void testConfirmRequest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("apartmentId")).thenReturn("1");
        when(request.getParameter("price")).thenReturn("10");
        adminController.confirmRequest(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testViewRequestNotFound() throws Exception {
        adminController.viewRequest(request, response);
        verifyForward();
        verify(request).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(AdminController.REQUEST_VIEW);
    }

    @Test
    public void testViewRequest() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        Request req = new Request();
        when(requestService.getById(anyLong())).thenReturn(Optional.of(req));
        when(apartmentService.listMostAppropriate(any(Pageable.class), eq(req))).
                thenReturn(mock(Page.class));
        adminController.viewRequest(request, response);
        verifyForward();
        verify(request).setAttribute(eq("request"), eq(req));
        verify(request).getRequestDispatcher(AdminController.REQUEST_VIEW);
    }

    @Test
    public void testViewRequests() throws Exception {
        when(requestService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewRequests(request, response);

        verify(request).getRequestDispatcher(AdminController.REQUESTS_VIEW);
        verifyForward();
    }

    @Test
    public void testViewApartments() throws Exception {
        when(apartmentService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewApartments(request, response);

        verify(request).getRequestDispatcher(AdminController.APARTMENTS_VIEW);
        verifyForward();
    }

    @Test
    public void testGetApartmentCreatePage() throws Exception {
        List<ApartmentType> list = Collections.singletonList(new ApartmentType());
        when(apartmentTypeDAO.list()).thenReturn(list);
        adminController.getApartmentCreatePage(request, response);

        verify(request).setAttribute("apartmentTypes", list);
        verify(request).getRequestDispatcher(AdminController.APARTMENT_CREATE_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateApartmentValidationError() throws Exception {
        adminController.createApartment(request, response);
        verify(request).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(AdminController.APARTMENT_CREATE_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateApartment() throws Exception {
        when(request.getParameter("apartmentTypeId")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("roomCount")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("20");
        adminController.createApartment(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testViewApartment() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        Apartment apartment = new Apartment();
        when(apartmentService.getById(anyLong())).thenReturn(apartment);
        adminController.viewApartment(request, response);
        verifyForward();
        verify(request).setAttribute(eq("apartment"), eq(apartment));
        verify(request).setAttribute(eq("apartmentTypes"), anyList());
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test
    public void testViewNoApartment() throws Exception {
        when(apartmentService.getById(or(any(Long.class), isNull()))).
                thenThrow(new ServiceException());
        adminController.viewApartment(request, response);
        verifyForward();
        verify(request).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test
    public void testUpdateApartmentWithValidationErrorAndNoId() throws Exception {
        when(apartmentService.getById(or(any(Long.class), isNull()))).
                thenThrow(new ServiceException());
        adminController.updateApartment(request, response);
        verify(request, times(2)).setAttribute(eq("errors"), anyList());
        verifyForward();
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test
    public void testUpdateApartmentWithValidationError() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        adminController.updateApartment(request, response);
        verify(request, times(1)).setAttribute(eq("errors"), anyList());
        verifyForward();
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test
    public void testUpdateApartmentWithServiceException() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("long name");
        when(request.getParameter("roomCount")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("apartmentTypeId")).thenReturn("1");
        doThrow(new ServiceException()).when(apartmentService).
                updateApartment(any(Apartment.class));
        adminController.updateApartment(request, response);
        verify(request, times(1)).setAttribute(eq("errors"), anyList());
        verifyForward();
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test
    public void testUpdateApartment() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("long name");
        when(request.getParameter("roomCount")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("apartmentTypeId")).thenReturn("1");
        adminController.updateApartment(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testGetOrders() throws Exception {
        when(orderService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewOrders(request, response);

        verify(request).getRequestDispatcher(AdminController.ORDERS_VIEW);
        verifyForward();
    }
}