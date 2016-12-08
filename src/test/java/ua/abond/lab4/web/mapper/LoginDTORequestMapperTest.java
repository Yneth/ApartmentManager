package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.web.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginDTORequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() {
        String login = "login";
        String password = "password";
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        LoginDTO map = new LoginDTORequestMapper().map(request);
        assertEquals(login, map.getLogin());
        assertEquals(password, map.getPassword());
    }

    @Test
    public void testMapEmptyRequest() {
        LoginDTO map = new LoginDTORequestMapper().map(request);
        assertNull(map.getLogin());
        assertNull(map.getPassword());
    }
}