package ua.abond.lab4.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.web.dto.UserSessionDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorityFilterTest {
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    private Filter filter;

    @Before
    public void setUp() {
        filter = new AuthorityFilter();
    }

    @Test(expected = NullPointerException.class)
    public void testInitWithNullAuthorityParam() throws Exception {
        filter.init(filterConfig);
    }

    @Test
    public void testDoFilterUnauthorized() throws Exception {
        mockInitParameter(filter, "admin");

        filter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test(expected = ServletException.class)
    public void testDoFilterNonHttp() throws Exception {
        ServletRequest req = mock(ServletRequest.class);
        ServletResponse resp = mock(ServletResponse.class);

        mockInitParameter(filter, "admin");

        filter.doFilter(req, resp, chain);
    }

    @Test
    public void testDoFilterSuccess() throws Exception {
        String auth = "admin";
        mockInitParameter(filter, auth);

        User user = new User();
        Authority authority = new Authority();
        authority.setName(auth);
        user.setAuthority(authority);
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new UserSessionDTO(user));

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    private void mockInitParameter(Filter filter, String param) throws Exception {
        when(filterConfig.getInitParameter(AuthorityFilter.AUTH_PARAM)).
                thenReturn(param);
        filter.init(filterConfig);
    }
}