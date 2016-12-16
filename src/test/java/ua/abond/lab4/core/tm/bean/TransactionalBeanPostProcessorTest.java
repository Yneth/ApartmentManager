package ua.abond.lab4.core.tm.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.BeanPostProcessor;
import ua.abond.lab4.core.annotation.Transactional;
import ua.abond.lab4.core.context.AnnotationBeanFactory;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionalBeanPostProcessorTest {
    @Mock
    private AnnotationBeanFactory beanFactory;

    private BeanPostProcessor beanPostProcessor;

    private Object bean;
    private String beanName;

    @Before
    public void testSetUp() {
        beanPostProcessor = new TransactionalBeanPostProcessor();
        bean = new Object();
        beanName = bean.getClass().getSimpleName();
    }

    @Test
    public void testPostProcessBeforeInitializationShouldDoNothing() {
        Object result =
                beanPostProcessor.postProcessBeforeInitialization(beanFactory, bean, beanName);
        assertEquals(bean, result);
    }

    @Test
    public void testPostProcessAfterInitializationShouldDoNothingForNonTransactionalObjects() {
        Object result =
                beanPostProcessor.postProcessAfterInitialization(beanFactory, bean, beanName);
        assertEquals(bean, result);
    }

    @Test
    public void testPostProcessAfterInitializationTransactionalObject() {
        Object result = beanPostProcessor.postProcessAfterInitialization(
                beanFactory,
                new TransactionalTest(),
                beanName
        );
        assertNotNull(result);
        assertNotEquals(bean, result);
    }

    public interface TransactionalInterface {
        @Transactional
        void method();
    }

    public static class TransactionalTest implements TransactionalInterface {
        @Override
        public void method() {

        }
    }
}