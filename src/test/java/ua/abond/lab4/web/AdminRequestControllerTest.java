package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.service.exception.ValidationException;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminRequestControllerTest extends ControllerTest {
    @Mock
    private RequestService requestService;
    @Mock
    private ApartmentService apartmentService;
    @InjectMocks
    private AdminRequestController adminController;


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
        verify(request).getRequestDispatcher(AdminRequestController.REQUEST_VIEW);
    }

    @Test
    public void testViewRequests() throws Exception {
        when(requestService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewRequests(request, response);

        verify(request).getRequestDispatcher(AdminRequestController.REQUESTS_VIEW);
        verifyForward();
    }

    @Test
    public void testDeleteRequest() throws Exception {
        adminController.rejectRequest(request, response);
        verify(response).sendRedirect(anyString());
    }
}
