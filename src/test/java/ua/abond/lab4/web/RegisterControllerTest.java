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

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {
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
    private RegisterController registerController;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(RegisterController.REGISTER_VIEW)).thenReturn(requestDispatcher);
    }

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
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testRegisterIfLoggedIn() throws Exception {
        mockUserToSession(create("test", "test"));
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        registerController.register(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testUnsuccessfulRegister() throws Exception {

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