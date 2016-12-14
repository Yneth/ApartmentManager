package ua.abond.lab4.service.impl;

import org.junit.Test;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.dao.jdbc.JdbcDAOTest;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.exception.ServiceException;

import static org.junit.Assert.assertTrue;

public class OrderServiceTest extends JdbcDAOTest {
    private static final String DATASET = "orders.xml";

    private OrderDAO orderDAO;
    private OrderService orderService;

    @Override
    public void onBeforeSetup() throws Exception {
        orderService = beanFactory.getBean(OrderService.class);
        orderDAO = beanFactory.getBean(OrderDAO.class);
        dataSet = loadDataSet(DATASET);
    }

    @Test
    public void testSuccessfulOrderPayment() throws Exception {
        orderService.payOrder(0L);
        Order order = orderDAO.getById(0L).orElse(null);
        assertTrue(order.isPayed());
    }

    @Test(expected = ServiceException.class)
    public void testNonExistingOrderPayment() throws Exception {
        orderService.payOrder(-1L);
    }

    @Test(expected = ServiceException.class)
    public void testPayPayedOrder() throws Exception {
        orderService.payOrder(1L);
    }
}