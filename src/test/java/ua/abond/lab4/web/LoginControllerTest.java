package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends ControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginController loginController;

    @Test
    public void testGetLoginPage() throws Exception {
        loginController.getLoginPage(request, response);

        verify(request).getRequestDispatcher(LoginController.LOGIN_VIEW);
        verifyForward();
    }

    @Test
    public void testGetLoginPageSendRedirect() throws Exception {
        User user = create("test", "test");
        mockUserToSession(user);

        loginController.getLoginPage(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    public void testLoginIfAlreadyLoggedIn() throws Exception {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);

        User user = create("test", "test");
        mockUserToSession(user);

        loginController.login(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        when(request.getSession()).thenReturn(httpSession);

        User user = create("test", "test");
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        loginController.login(request, response);
        verify(response).sendRedirect("/");
        verify(httpSession).setAttribute("user", user);
    }

    @Test
    public void testUnsuccessfulLogin() throws Exception {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);

        loginController.login(request, response);

        verify(request).setAttribute("errors", Collections.singletonList("Wrong credentials."));
        verify(request).getRequestDispatcher(LoginController.LOGIN_VIEW);
        verifyForward();
    }
}