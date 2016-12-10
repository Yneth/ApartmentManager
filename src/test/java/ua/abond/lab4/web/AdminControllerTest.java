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
import ua.abond.lab4.service.exception.ValidationException;

import java.util.Collections;
import java.util.List;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.isNull;

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

    @Test(expected = ValidationException.class)
    public void testConfirmRequestValidationException() throws Exception {
        mockThrowValidationException(Request.class);
        adminController.confirmRequest(request, response);
    }

    @Test
    public void testConfirmRequest() throws Exception {
        adminController.confirmRequest(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ServiceException.class)
    public void testViewRequestNotFound() throws Exception {
        when(requestService.getById(or(any(Long.class), isNull()))).
                thenThrow(new ServiceException());
        adminController.viewRequest(request, response);
    }

    @Test
    public void testViewRequest() throws Exception {
        when(apartmentService.listMostAppropriate(isNull(), isNull())).
                thenReturn(mock(Page.class));
        adminController.viewRequest(request, response);
        verifyForward();
        verify(request).setAttribute(eq("request"), isNull());
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

    @Test(expected = ValidationException.class)
    public void testCreateApartmentValidationException() throws Exception {
        mockThrowValidationException(Apartment.class);
        adminController.createApartment(request, response);
    }

    @Test
    public void testCreateApartment() throws Exception {
        adminController.createApartment(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testViewApartment() throws Exception {
        Apartment apartment = new Apartment();
        when(apartmentService.getById(isNull())).thenReturn(apartment);
        adminController.viewApartment(request, response);
        verifyForward();
        verify(request).setAttribute(eq("apartment"), eq(apartment));
        verify(request).setAttribute(eq("apartmentTypes"), anyList());
        verify(request).getRequestDispatcher(AdminController.APARTMENT_VIEW);
    }

    @Test(expected = ServiceException.class)
    public void testUpdateApartmentWithServiceException() throws Exception {
        doThrow(new ServiceException()).
                when(apartmentService).updateApartment(isNull());
        adminController.updateApartment(request, response);
    }

    @Test(expected = ValidationException.class)
    public void testUpdateApartmentWithException() throws Exception {
        mockThrowValidationException(Apartment.class);
        adminController.updateApartment(request, response);
        verify(response, never()).sendError(anyInt());
    }

    @Test
    public void testUpdateApartment() throws Exception {
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