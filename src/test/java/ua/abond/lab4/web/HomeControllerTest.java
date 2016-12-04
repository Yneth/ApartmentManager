package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void testGetIndexPage() throws Exception {
        when(request.getRequestDispatcher(HomeController.HOME_VIEW)).thenReturn(requestDispatcher);

        new HomeController().getIndexPage(request, response);

        verify(request).getRequestDispatcher(HomeController.HOME_VIEW);
        verify(requestDispatcher).forward(request, response);
    }
}