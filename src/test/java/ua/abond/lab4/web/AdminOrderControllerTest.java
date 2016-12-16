package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.service.OrderService;

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
}