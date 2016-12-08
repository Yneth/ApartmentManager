package ua.abond.lab4.config.core.web.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.exception.ApplicationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FrontControllerServletTest {
    private static final String CONTEXT_CONF_LOCATION = "ua.abond.lab4.config.core.web.servlet.config";
    @Mock
    private ServletConfig config;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    private HttpServlet servlet;

    @Before
    public void setUp() {
        servlet = new FrontControllerServlet();
        when(request.getRequestDispatcher(anyString())).
                thenReturn(dispatcher);
    }

    @Test(expected = NullPointerException.class)
    public void testInitWithNoContextConfigLocation() throws Exception {
        servlet.init(config);
    }

    @Test
    public void testInit() throws Exception {
        when(config.getInitParameter("contextConfigLocation")).
                thenReturn("ua.abond.lab4.db");
        servlet.init(config);
        verify(config, times(1)).getInitParameter("contextConfigLocation");
    }

    @Test(expected = ApplicationException.class)
    public void testInitWithAbstractContextClass() throws Exception {
        when(config.getInitParameter("contextClass")).
                thenReturn("ua.abond.lab4.config.core.ConfigurableBeanFactory");
        servlet.init(config);
    }

    @Test(expected = NullPointerException.class)
    public void testInitWithNullContextConfigLocation() throws ServletException {
        servlet.init(config);
    }

    @Test(expected = ApplicationException.class)
    public void testInitWithWrongInterface() throws Exception {
        when(config.getInitParameter("contextClass")).
                thenReturn("ua.abond.lab4.config.core.BeanFactory");
        servlet.init(config);
    }

    @Test
    public void testNoMappingForGet() throws Exception {
        initServletWithConfig();
        mockMethod("GET");
        mockURI("test");

        servlet.service(request, response);

        verify(response).sendError(anyInt());
    }

    @Test
    public void testSuccessfulDoGet() throws Exception {
        initServletWithConfig();
        mockMethod("GET");
        mockURI("/");
        servlet.service(request, response);

        verify(request).getRequestDispatcher(anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testSuccessfulDoPost() throws Exception {
        initServletWithConfig();
        mockMethod("POST");
        mockURI("/");

        servlet.service(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testNoMappingForPost() throws Exception {
        initServletWithConfig();
        mockMethod("POST");
        mockURI("test");
        servlet.service(request, response);

        verify(response).sendError(anyInt());
    }

    @Test
    public void testExceptionController() throws Exception {
        initServletWithConfig();
        mockMethod("GET");
        mockURI("/exception");

        servlet.service(request, response);

        verify(request).setAttribute(eq("error"), eq("error"));
    }

    @Test
    public void testExceptionControllerThatItselfThrowsException() throws Exception {
        initServletWithConfig();
        mockMethod("GET");
        mockURI("/runtime");

        servlet.service(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private void mockMethod(String method) {
        when(request.getMethod()).
                thenReturn(method);
    }

    private void mockURI(String uri) {
        when(request.getRequestURI()).
                thenReturn(uri);
    }

    private void initServletWithConfig() throws ServletException {
        when(config.getInitParameter("contextConfigLocation")).
                thenReturn(CONTEXT_CONF_LOCATION);
        servlet.init(config);
    }
}