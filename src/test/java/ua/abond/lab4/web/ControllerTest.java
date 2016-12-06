package ua.abond.lab4.web;

import org.junit.Before;
import org.mockito.Mock;
import ua.abond.lab4.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpSession httpSession;
    @Mock
    protected RequestDispatcher requestDispatcher;

    @Before
    public void setUpRequestDispatcher() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    protected void mockUserToSession(User user) {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(user);
    }

    protected void verifyForward() throws Exception {
        verify(requestDispatcher).forward(request, response);
    }

    protected User create(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
