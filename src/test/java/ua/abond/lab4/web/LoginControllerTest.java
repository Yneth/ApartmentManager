package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(LoginController.LOGIN_VIEW)).thenReturn(requestDispatcher);
    }

    @Test
    public void testGetLoginPage() throws Exception {
        loginController.getLoginPage(request, response);

        verify(request).getRequestDispatcher(LoginController.LOGIN_VIEW);
        verify(requestDispatcher).forward(request, response);
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
        verify(requestDispatcher).forward(request, response);
    }

    private void mockUserToSession(User user) {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(user);
    }

    private User create(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}