package ua.abond.lab4.service.impl;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.dao.OrderDAO;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.exception.ServiceException;

import static junit.framework.TestCase.assertTrue;

public class OrderServiceTest {
    private static final String DATASET = "orders-dataset.xml";
    private static final String TEST_PACKAGE = "ua.abond.lab4.db";

    private IDatabaseTester tester;
    private OrderDAO orderDAO;
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        BeanFactory bf = new AnnotationBeanFactory(TEST_PACKAGE);
        orderService = bf.getBean(OrderService.class);
        orderDAO = bf.getBean(OrderDAO.class);
        tester = bf.getBean(IDatabaseTester.class);
        tester.setDataSet(new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(DATASET)
        ));
        tester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
        tester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
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