package ua.abond.lab4.config.core.bean;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import static org.junit.Assert.*;

public class InjectAnnotationBeanPostProcessorTest {
    private AnnotationBeanFactory bf;

    @Before
    public void setUp() {
        bf = new AnnotationBeanFactory();
    }

    @Test
    public void testCyclicInjection() {
        BeanDefinition aBd = new BeanDefinition(A.class);
        BeanDefinition bBd = new BeanDefinition(B.class);
        bf.register(aBd);
        bf.register(bBd);

        bf.createBean("A", aBd);

        assertNotNull(bf.getBean("A"));
    }

    private static class A {
        @Inject
        B b;
    }

    private static class B {
        @Inject
        A a;
    }
}