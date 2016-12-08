package ua.abond.lab4.config.core.bean;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import static org.junit.Assert.*;

public class InjectAnnotationBeanPostProcessorTest {
    private BeanPostProcessor bpp;
    private AnnotationBeanFactory bf;

    @Before
    public void setUp() {
        bf = new AnnotationBeanFactory();
        bpp = new InjectAnnotationBeanPostProcessor();
    }

    @Test
    public void testBeanPostProcessBeforeInitializationShouldReturnTheSameValue() {
        Object expected = new Object();
        Object actual = bpp.postProcessBeforeInitialization(bf, expected, "");
        assertEquals(expected, actual);
    }

    @Test
    public void testBeanPostProcessAfterShouldInject() {
        TestClass ts = new TestClass();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, ts.getClass().getSimpleName());
        assertNotNull(ts);
        assertNotNull(ts.empty);
        assertEquals(bf.getBean(Empty.class), ts.empty);
    }

    @Test
    public void testSetterInjection() {
        TestSetter ts = new TestSetter();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, "");
        assertNotNull(ts.getEmpty());
        assertEquals(ts.getEmpty(), bf.getBean(Empty.class));
    }

    @Test
    public void testSetterInjectionSetterDoesNotSet() {
        TestNullSetter ts = new TestNullSetter();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, "");
        assertNull(ts.getEmpty());
    }

    @Test(expected = BeanInstantiationException.class)
    public void testNoArgSetterInjection() {
        TestNoArgSetter ts = new TestNoArgSetter();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, "");
    }

    @Test(expected = BeanInstantiationException.class)
    public void testNoArgSetterInjectionInjectOnField() {
        TestNoArgSetter ts = new TestNoArgSetter();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, "");
    }

    @Test(expected = BeanInstantiationException.class)
    public void testPrivateSetterInjection() {
        TestPrivateSetterInjection ts = new TestPrivateSetterInjection();
        bf.register(new BeanDefinition(Empty.class));
        bpp.postProcessAfterInitialization(bf, ts, "");
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

    private static class TestPrivateSetterInjection {
        private Empty empty;

        @Inject
        private void setEmpty(Empty empty) {
        }

        public Empty getEmpty() {
            return empty;
        }
    }

    private static class TestNoArgSetterInjectField {
        @Inject
        private Empty empty;

        public void setEmpty() {
        }

        public Empty getEmpty() {
            return empty;
        }
    }

    private static class TestNoArgSetter {
        private Empty empty;

        @Inject
        public void setEmpty() {
        }

        public Empty getEmpty() {
            return empty;
        }
    }

    private static class TestNullSetter {
        private Empty empty;

        @Inject
        public void setEmpty(Empty empty) {
        }

        public Empty getEmpty() {
            return empty;
        }
    }

    private static class TestSetter {
        private Empty empty;

        @Inject
        public void setEmpty(Empty empty) {
            this.empty = empty;
        }

        public Empty getEmpty() {
            return empty;
        }
    }

    private static class Empty {

    }

    private static class TestClass {
        @Inject
        private Empty empty;
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