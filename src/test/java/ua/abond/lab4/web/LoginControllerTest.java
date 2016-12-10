package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.web.dto.LoginDTO;
import ua.abond.lab4.web.dto.UserSessionDTO;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

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
        when(mapperService.map(request, UserSessionDTO.class)).
                thenReturn(mock(UserSessionDTO.class));

        loginController.getLoginPage(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    public void testLoginIfAlreadyLoggedIn() throws Exception {
        when(mapperService.map(request, UserSessionDTO.class)).
                thenReturn(mock(UserSessionDTO.class));

        loginController.login(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        User user = new User();
        user.setPassword("123");
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("123");

        when(mapperService.map(request, LoginDTO.class)).thenReturn(loginDTO);
        when(userService.findByLogin(isNull())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(httpSession);

        loginController.login(request, response);
        verify(response).sendRedirect("/");
        verify(httpSession).setAttribute(eq("user"), any(UserSessionDTO.class));
    }

    @Test
    public void testUnsuccessfulLogin() throws Exception {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        when(mapperService.map(request, LoginDTO.class)).thenReturn(mock(LoginDTO.class));

        loginController.login(request, response);

        verify(request).setAttribute("errors", Collections.singletonList("Wrong credentials."));
        verify(request).getRequestDispatcher(LoginController.LOGIN_VIEW);
        verifyForward();
    }
}