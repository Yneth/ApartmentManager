package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.service.exception.ValidationException;

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
        when(userService.listAdmins(isNull())).
                thenReturn(mock(Page.class));
        superUserController.viewAdmins(request, response);
        verifyForward();
        verify(request, never()).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(SuperUserController.ADMINS_VIEW);
    }

    @Test(expected = ServiceException.class)
    public void testViewAdminsWithServiceException() throws Exception {
        when(userService.listAdmins(isNull())).
                thenThrow(new ServiceException());
        superUserController.viewAdmins(request, response);
    }

    @Test
    public void testGetCreateAdminPage() throws Exception {
        superUserController.getCreateAdminPage(request, response);
        verify(request).getRequestDispatcher(SuperUserController.CREATE_ADMIN_VIEW);
        verifyForward();
    }

    @Test
    public void testCreateAdmin() throws Exception {
        superUserController.createAdmin(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ValidationException.class)
    public void testCreateAdminWithValidationError() throws Exception {
        mockThrowValidationException(User.class);
        superUserController.createAdmin(request, response);
    }

    @Test(expected = ServiceException.class)
    public void testCreateAdminWithServiceException() throws Exception {
        doThrow(new ServiceException()).when(userService).createAdmin(isNull());
        superUserController.createAdmin(request, response);
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        superUserController.deleteAdmin(request, response);
        verify(userService).deleteAdminById(or(any(Long.class), isNull()));
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteAdminWithServiceException() throws Exception {
        doThrow(new ServiceException()).when(userService).
                deleteAdminById(or(any(Long.class), isNull()));

        superUserController.deleteAdmin(request, response);
    }
}