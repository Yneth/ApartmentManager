package ua.abond.lab4.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharsetFilterTest {
    private Filter filter;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        filter = new CharsetFilter();
    }

    @Test
    public void testInitWithDefaultCharset() throws Exception {
        when(filterConfig.getInitParameter(anyString())).
                thenReturn(null);
        filter.init(filterConfig);
        verify(filterConfig).getInitParameter(anyString());

        assertEquals(CharsetFilter.DEFAULT_ENCODING, ((CharsetFilter) filter).getEncoding());
    }

    @Test
    public void testInitWithCustomCharset() throws Exception {
        String testCharset = "test";
        when(filterConfig.getInitParameter(anyString())).
                thenReturn(testCharset);
        filter.init(filterConfig);
        verify(filterConfig).getInitParameter(anyString());

        assertEquals(testCharset, ((CharsetFilter) filter).getEncoding());
    }

    @Test
    public void testDoFilterSetEncoding() throws Exception {
        filter.doFilter(request, response, chain);
        verify(request).setCharacterEncoding(or(isNull(), anyString()));
        verify(response).setCharacterEncoding(or(isNull(), anyString()));
    }
}