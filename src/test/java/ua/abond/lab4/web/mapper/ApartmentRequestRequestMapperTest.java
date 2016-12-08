package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.RequestStatus;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentRequestRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        when(request.getParameter("from")).thenReturn("2015-10-20T10:22");
        when(request.getParameter("to")).thenReturn("2016-10-20T10:22");
        when(request.getParameter("roomCount")).thenReturn("1");
        when(request.getParameter("apartmentTypeId")).thenReturn("1");
        when(request.getParameter("status")).thenReturn("confirmed");
        when(request.getParameter("statusComment")).thenReturn("test");
        Request map = new ApartmentRequestRequestMapper().map(request);
        assertEquals(1, map.getLookup().getRoomCount());
        assertEquals(new Long(1), map.getLookup().getType().getId());
        assertEquals(RequestStatus.CONFIRMED, map.getStatus());
        assertEquals("test", map.getStatusComment());
        assertEquals(LocalDateTime.of(2015, 10, 20, 10, 22), map.getFrom());
        assertEquals(LocalDateTime.of(2016, 10, 20, 10, 22), map.getTo());
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