package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.web.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDTORequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("requestId")).thenReturn("1");
        when(request.getParameter("apartmentId")).thenReturn("1");
        when(request.getParameter("price")).thenReturn("100");

        OrderDTO map = new OrderDTORequestMapper().map(request);
        assertEquals(new Long(1L), map.getId());
        assertEquals(new Long(1L), map.getApartmentId());
        assertEquals(new Long(1L), map.getRequestId());
        assertEquals(new BigDecimal(100), map.getPrice());
    }

    @Test
    public void testMapEmptyRequest() throws Exception {
        OrderDTO map = new OrderDTORequestMapper().map(request);
        assertNull(map.getId());
        assertNull(map.getPrice());
        assertNull(map.getRequestId());
        assertNull(map.getApartmentId());
    }
}