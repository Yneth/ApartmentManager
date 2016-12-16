package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.support.DefaultPageable;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.SortOrder;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageableRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        when(request.getParameter("page")).thenReturn("20");
        when(request.getParameter("pageSize")).thenReturn("40");
        when(request.getParameter("order")).thenReturn("desc");
        DefaultPageable map = new PageableRequestMapper().map(request);
        assertEquals(20, map.getPageNumber());
        assertEquals(40, map.getPageSize());
        assertEquals(SortOrder.DESC, map.getSortOrder());
    }

    @Test
    public void testMapEmptyRequest() throws Exception {
        Pageable pageable = new PageableRequestMapper().map(request);
        assertEquals(10, pageable.getPageSize());
        assertEquals(SortOrder.ASC, pageable.getSortOrder());
        assertEquals(1, pageable.getPageNumber());
        assertEquals(0, pageable.getOffset());
    }
}