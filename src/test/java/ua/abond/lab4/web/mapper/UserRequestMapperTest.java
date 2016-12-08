package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        String login = "login";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";

        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("firstName")).thenReturn(firstName);
        when(request.getParameter("lastName")).thenReturn(lastName);

        User map = new UserRequestMapper().map(request);
        assertEquals(login, map.getLogin());
        assertEquals(password, map.getPassword());
        assertEquals(firstName, map.getFirstName());
        assertEquals(lastName, map.getLastName());
    }

    @Test
    public void testMapEmptyRequest() throws Exception {
        User map = new UserRequestMapper().map(request);
        assertNull(map.getPassword());
        assertNull(map.getFirstName());
        assertNull(map.getLogin());
        assertNull(map.getLastName());
    }
}