package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.exception.ServiceException;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminOrderControllerTest extends ControllerTest {
    @Mock
    private OrderService orderService;
    @InjectMocks
    private AdminOrderController adminController;


    @Test
    public void testGetOrders() throws Exception {
        when(orderService.list(or(any(Pageable.class), isNull()))).
                thenReturn(mock(Page.class));

        adminController.viewOrders(request, response);

        verify(request).getRequestDispatcher(AdminOrderController.ORDERS_VIEW);
        verifyForward();
    }

    @Test
    public void testPayOrder() throws Exception {
        adminController.payOrder(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = ServiceException.class)
    public void testPayOrderWithException() throws Exception {
        doThrow(new ServiceException()).when(orderService).
                payOrder(or(isNull(), any(Long.class)));
        adminController.payOrder(request, response);
    }
}