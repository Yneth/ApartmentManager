package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest extends ControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private RegisterController registerController;

    @Test
    public void testGetRegisterPageRedirectIfLoggedIn() throws Exception {
        mockUserToSession(create("test", "test"));
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        registerController.getRegisterPage(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        registerController.getRegisterPage(request, response);
        verifyForward();
    }

    @Test
    public void testRegisterIfLoggedIn() throws Exception {
        mockUserToSession(create("test", "test"));
        registerController.register(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testUnsuccessfulRegisterFailedOnValidation() throws Exception {
        User user = create("user", "user");
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        registerController.register(request, response);
        verify(request).setAttribute(eq("errors"), anyList());
        verify(userService, never()).register(user);

        verify(request).getRequestDispatcher(RegisterController.REGISTER_VIEW);
        verifyForward();
    }

    @Test
    public void testUnsuccessfulRegisterAlreadyExists() throws Exception {
        User user = create("testtest", "testtest");
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        doThrow(new ServiceException()).when(userService).register(user);

        registerController.register(request, response);

        verify(userService).register(user);

        verify(request).setAttribute(eq("errors"), anyList());
        verify(request).getRequestDispatcher(RegisterController.REGISTER_VIEW);
        verifyForward();
    }

    @Test
    public void testSuccessfulRegister() throws Exception {
        User user = create("testtest", "testtest");
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        registerController.register(request, response);

        verify(userService).register(user);
        verify(response).sendRedirect(anyString());
    }
}