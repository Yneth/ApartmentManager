package ua.abond.lab4.web.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmRequestDTORequestMapperTest {
    @Mock
    private HttpServletRequest request;

    @Test
    public void testMap() {
        Long requestId = 1L;
        Long apartmentId = 1L;
        BigDecimal price = new BigDecimal(100);
        when(request.getParameter("id")).thenReturn("" + requestId);
        when(request.getParameter("apartmentId")).thenReturn("" + apartmentId);
        when(request.getParameter("price")).thenReturn("" + price);
        ConfirmRequestDTO map = new ConfirmRequestDTORequestMapper().map(request);
        assertEquals(requestId, map.getRequestId());
        assertEquals(price, map.getPrice());
        assertEquals(apartmentId, map.getApartmentId());
    }

    @Test
    public void testMapNulls() {
        ConfirmRequestDTO map = new ConfirmRequestDTORequestMapper().map(request);
        assertEquals(null, map.getRequestId());
        assertEquals(null, map.getPrice());
        assertEquals(null, map.getApartmentId());
    }
}