package ua.abond.lab4.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocaleCookieFilterTest {
    private Filter filter;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        filter = new LocaleCookieFilter();
    }

    @Test
    public void testDoFilterSetsRequestAttribute() throws Exception {
        String lang = "en";
        when(request.getParameter(LocaleCookieFilter.LANG_KEY)).
                thenReturn(lang);
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request).setAttribute(LocaleCookieFilter.LANG_KEY, lang);
    }

    @Test
    public void testCreatesCookie() throws Exception {
        String lang = "en";
        when(request.getParameter(LocaleCookieFilter.LANG_KEY)).
                thenReturn(lang);
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response).addCookie(notNull());
    }

    @Test
    public void testGetFromCookieIfNoParamAndNoCookie() throws Exception {
        when(request.getCookies()).
                thenReturn(new Cookie[0]);
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request).getCookies();
        verify(request, never()).setAttribute(
                or(anyString(), isNull()),
                or(any(String.class), isNull())
        );
    }

    @Test
    public void setLangFromCookie() throws Exception {
        String lang = "en";
        when(request.getCookies()).
                thenReturn(new Cookie[]{new Cookie(LocaleCookieFilter.LANG_KEY, lang)});
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(request).getCookies();
        verify(request).setAttribute(LocaleCookieFilter.LANG_KEY, lang);
    }
}