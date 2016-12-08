package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.domain.ApartmentType;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentTypeRequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        when(request.getParameter("apartmentTypeId")).thenReturn("1");
        ApartmentType map = new ApartmentTypeRequestMapper().map(request);
        assertEquals(new Long(1), map.getId());
    }

    @Test
    public void testMapEmptyRequest() throws Exception {
        ApartmentType map = new ApartmentTypeRequestMapper().map(request);
        assertNull(map.getId());
        assertNull(map.getName());
    }
}