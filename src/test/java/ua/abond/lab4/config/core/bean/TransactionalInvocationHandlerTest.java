package ua.abond.lab4.config.core.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.tm.TransactionManager;
import ua.abond.lab4.config.core.tm.bean.TransactionalInvocationHandler;

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
    public void testSuccessfulInvoke() throws Throwable {
        handler.invoke(null, testMethod, new Object[]{});
        verify(tm).begin();
        verify(tm).commit();
        verify(tm).end();
    }

    @Test(expected = RuntimeException.class)
    public void testInvokeWithException() throws Throwable {
        doThrow(new RuntimeException()).when(testClass).test();
        try {
            handler.invoke(null, testMethod, new Object[]{});
        } finally {
            verify(tm).begin();
            verify(tm).rollback();
            verify(tm).end();
        }
    }

    public interface TestInterface {
        @Transactional
        void test();
    }
}