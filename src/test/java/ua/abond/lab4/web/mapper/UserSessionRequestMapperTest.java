package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RunWith(MockitoJUnitRunner.class)
public class UserSessionRequestMapperTest {
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() {
        
    }
}