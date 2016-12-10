package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.LoginIsAlreadyTakenException;
import ua.abond.lab4.service.exception.ValidationException;
import ua.abond.lab4.web.dto.UserSessionDTO;

import static org.mockito.AdditionalMatchers.or;
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
        when(mapperService.map(request, UserSessionDTO.class)).
                thenReturn(mock(UserSessionDTO.class));
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
        when(mapperService.map(request, UserSessionDTO.class)).
                thenReturn(mock(UserSessionDTO.class));
        registerController.register(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ValidationException.class)
    public void testUnsuccessfulRegisterFailedOnValidation() throws Exception {
        mockThrowValidationException(User.class);
        registerController.register(request, response);
        verify(userService, never()).register(or(any(User.class), isNull()));
        verify(request, never()).getRequestDispatcher(RegisterController.REGISTER_VIEW);
    }

    @Test(expected = LoginIsAlreadyTakenException.class)
    public void testUnsuccessfulRegisterAlreadyExists() throws Exception {
        when(mapperService.map(request, User.class)).
                thenReturn(mock(User.class));
        doThrow(new LoginIsAlreadyTakenException()).
                when(userService).
                register(any(User.class));

        registerController.register(request, response);
    }

    @Test
    public void testSuccessfulRegister() throws Exception {
        when(mapperService.map(request, User.class)).
                thenReturn(mock(User.class));

        registerController.register(request, response);

        verify(userService).register(any(User.class));
        verify(response).sendRedirect(anyString());
    }
}