package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.Apartment;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void map() throws Exception {
        Long id = 100L;
        String name = "name";
        int roomCount = 10;

        when(request.getParameter("id")).thenReturn(id + "");
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("roomCount")).thenReturn(roomCount + "");
        Apartment map = new ApartmentRequestMapper().map(request);
        assertEquals(id, map.getId());
        assertEquals(name, map.getName());
        assertEquals(roomCount, map.getRoomCount());
    }

    @Test
    public void testMapOnNullValues() {
        Apartment map = new ApartmentRequestMapper().map(request);
        assertEquals(null, map.getId());
        assertEquals("", map.getName());
        assertEquals(0, map.getRoomCount());
    }
}