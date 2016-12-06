package ua.abond.lab4.web.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSessionRequestMapperTest {
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;

    @Before
    public void setUpSession() {
        when(request.getSession(anyBoolean())).
                thenReturn(session);
    }

    @Test
    public void testMap() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        when(session.getAttribute("user")).thenReturn(user);

        User map = new UserSessionRequestMapper().map(request);
        assertNotNull(map);
        assertEquals(user.getLogin(), map.getLogin());
        assertEquals(user.getPassword(), map.getPassword());
    }

    @Test
    public void testMapEmptyRequest() {
        assertNull(new UserSessionRequestMapper().map(request));
    }
}