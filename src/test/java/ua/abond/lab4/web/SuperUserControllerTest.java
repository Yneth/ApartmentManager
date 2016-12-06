package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SuperUserControllerTest extends ControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private SuperUserController superUserController;

    @Test
    public void testViewAdmins() throws Exception {
        when(userService.listAdmins(any(Pageable.class))).
                thenReturn(mock(Page.class));
        superUserController.viewAdmins(request, response);
        verifyForward();
        verify(request, never()).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(SuperUserController.ADMINS_VIEW);
    }

    @Test
    public void testViewAdminsWithServiceException() throws Exception {
        when(userService.listAdmins(any(Pageable.class))).
                thenThrow(new ServiceException());
        superUserController.viewAdmins(request, response);
        verifyForward();
        verify(request).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(SuperUserController.ADMINS_VIEW);
    }

    @Test
    public void testGetCreateAdminPage() throws Exception {
        superUserController.getCreateAdminPage(request, response);
        verify(request).getRequestDispatcher(SuperUserController.CREATE_ADMIN_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateAdmin() throws Exception {
        when(request.getParameter("login")).thenReturn("12345678");
        when(request.getParameter("password")).thenReturn("12345678");
        superUserController.createAdmin(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testCreateAdminWithValidationError() throws Exception {
        superUserController.createAdmin(request, response);
        verifyForward();
        verify(request).getRequestDispatcher(SuperUserController.CREATE_ADMIN_VIEW);
        verify(request, times(1)).setAttribute(eq("errors"), anyList());
    }

    @Test
    public void testCreateAdminWithServiceException() throws Exception {
        when(request.getParameter("login")).thenReturn("12345678");
        when(request.getParameter("password")).thenReturn("12345678");
        doThrow(new ServiceException()).when(userService).createAdmin(any(User.class));
        superUserController.createAdmin(request, response);
        verifyForward();
        verify(request).getRequestDispatcher(SuperUserController.CREATE_ADMIN_VIEW);
        verify(request, times(1)).setAttribute(eq("errors"), anyList());
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        superUserController.deleteAdmin(request, response);
        verify(userService).deleteAdminById(or(any(Long.class), isNull()));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDeleteAdminWithServiceException() throws Exception {
        when(userService.listAdmins(any(Pageable.class))).
                thenReturn(mock(Page.class));
        doThrow(new ServiceException()).when(userService).
                deleteAdminById(or(any(Long.class), isNull()));

        superUserController.deleteAdmin(request, response);
        verify(userService).deleteAdminById(or(any(Long.class), isNull()));

        verifyForward();
        verify(request).getRequestDispatcher(SuperUserController.ADMINS_VIEW);
    }
}