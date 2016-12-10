package ua.abond.lab4.config.core.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.exception.TransactionException;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import java.lang.reflect.Method;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionalInvocationHandlerTest {
    @Mock
    private TransactionManager tm;
    @Mock
    private TestInterface testClass;
    private Method testMethod;
    private TransactionalInvocationHandler handler;

    @Before
    public void setUp() throws Exception {
        testMethod = TestInterface.class.getMethod("test");
        handler = new TransactionalInvocationHandler(testClass, tm);
    }

    @Test
    public void testSuccessfulInvoke() throws Exception {
        handler.invoke(null, testMethod, new Object[]{});
        verify(tm).createConnection();
        verify(tm).commit();
        verify(tm).releaseConnection();
    }

    @Test(expected = TransactionException.class)
    public void testInvokeWithException() throws Exception {
        doThrow(new RuntimeException()).when(testClass).test();
        try {
            handler.invoke(null, testMethod, new Object[]{});
        } finally {
            verify(tm).createConnection();
            verify(tm).rollback();
            verify(tm).releaseConnection();
        }
    }

    public interface TestInterface {
        @Transactional
        void test();
    }
}