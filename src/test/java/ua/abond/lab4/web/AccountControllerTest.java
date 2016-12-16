package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.web.dto.ChangePasswordDTO;
import ua.abond.lab4.web.dto.UserSessionDTO;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends ControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private AccountController accountController;

    @Test
    public void testViewAccount() throws Exception {
        mockUserToMapperService();
        accountController.viewAccount(request, response);
        verifyForward();
        verify(request).getRequestDispatcher(AccountController.ACCOUNT_VIEW);
    }

    @Test
    public void testViewAccountIfNotLoggedIn() throws Exception {
        accountController.viewAccount(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void updateAccount() throws Exception {
        mockUserToMapperService();
        when(mapperService.map(request, User.class)).
                thenReturn(mock(User.class));
        accountController.updateAccount(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void updateAccountIfNotLoggedIn() throws Exception {
        accountController.updateAccount(request, response);
        verify(response).sendRedirect(anyString());
        verify(mapperService, never()).map(request, User.class);
    }

    @Test
    public void changePassword() throws Exception {
        mockUserToMapperService();
        when(mapperService.map(request, ChangePasswordDTO.class)).
                thenReturn(mock(ChangePasswordDTO.class));
        accountController.changePassword(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void changePasswordIfNotLoggedIn() throws Exception {
        accountController.changePassword(request, response);
        verify(response).sendRedirect(anyString());
        verify(mapperService, never()).map(request, ChangePasswordDTO.class);
    }

    private void mockUserToMapperService() {
        when(mapperService.map(request, UserSessionDTO.class)).
                thenReturn(mock(UserSessionDTO.class));
    }
}