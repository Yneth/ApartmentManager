package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;

    private LogoutController logoutController;

    @Before
    public void setUp() {
        logoutController = new LogoutController();
    }

    @Test
    public void testLogout() throws Exception {
        when(request.getSession(false)).thenReturn(httpSession);
        logoutController.logout(request, response);
        verify(httpSession).invalidate();
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testLogoutIfSessionIsInvalidated() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        logoutController.logout(request, response);
        verify(response).sendRedirect(anyString());
    }
}