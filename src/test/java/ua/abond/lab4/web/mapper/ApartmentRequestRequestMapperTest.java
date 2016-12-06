package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentRequestRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        when(request.getParameter("from")).thenReturn("2015-10-20T10:22");
        when(request.getParameter("to")).thenReturn("2016-10-20T10:22");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("roomCount")).thenReturn("1");
        when(request.getParameter("status")).thenReturn("1");
        when(request.getParameter("statusComment")).thenReturn("test");
        ApartmentType map = new ApartmentTypeRequestMapper().map(request);
        assertEquals(new Long(1), map.getId());
    }

    @Test
    public void testMapEmptyRequest() throws Exception {
        Request map = new ApartmentRequestRequestMapper().map(request);
        assertNull(map.getId());
        assertNull(map.getFrom());
        assertNull(map.getTo());
        assertNotNull(map.getLookup());
        assertEquals(0, map.getLookup().getRoomCount());
        assertNull(map.getLookup().getType().getId());
        assertNull(map.getStatus());
        assertNull(map.getStatusComment());
    }
}