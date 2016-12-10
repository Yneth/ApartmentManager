package ua.abond.lab4.web;

import org.junit.Before;
import org.mockito.Mock;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.service.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class ControllerTest {
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpSession httpSession;
    @Mock
    protected RequestDispatcher requestDispatcher;
    @Mock
    protected RequestMapperService mapperService;
    @Mock
    protected ValidationService validationService;

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

    protected <T> void mockThrowValidationException(Class<T> type) throws ValidationException {
        doThrow(new ValidationException(Collections.emptyList())).
                when(validationService).
                validate(or(any(type), isNull()));
    }

    protected User create(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
