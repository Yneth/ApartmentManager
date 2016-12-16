package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.service.exception.ValidationException;

import java.util.Collections;
import java.util.List;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminApartmentControllerTest extends ControllerTest {
    @Mock
    private ApartmentService apartmentService;
    @Mock
    private ApartmentTypeDAO apartmentTypeDAO;
    @InjectMocks
    private AdminApartmentController adminController;

    @Test
    public void testViewApartments() throws Exception {
        when(apartmentService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewApartments(request, response);

        verify(request).getRequestDispatcher(AdminApartmentController.APARTMENTS_VIEW);
        verifyForward();
    }

    @Test
    public void testGetApartmentCreatePage() throws Exception {
        List<ApartmentType> list = Collections.singletonList(new ApartmentType());
        when(apartmentTypeDAO.list()).thenReturn(list);
        adminController.getApartmentCreatePage(request, response);

        verify(request).setAttribute("apartmentTypes", list);
        verify(request).getRequestDispatcher(AdminApartmentController.APARTMENT_CREATE_VIEW);
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
        verify(request).getRequestDispatcher(AdminApartmentController.APARTMENT_VIEW);
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
}
