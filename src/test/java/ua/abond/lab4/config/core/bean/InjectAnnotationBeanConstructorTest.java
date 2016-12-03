package ua.abond.lab4.config.core.bean;

import org.junit.Test;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;
import ua.abond.lab4.config.core.exception.ImproperlyConfiguredException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class InjectAnnotationBeanConstructorTest {

    @Test(expected = BeanInstantiationException.class)
    public void testNoAvailableConstructor() {
        AnnotationBeanFactory bf = new AnnotationBeanFactory();
        bf.register(new BeanDefinition(String.class));
        new InjectAnnotationBeanConstructor().create(bf, "", new BeanDefinition(String.class));
    }

    @Test
    public void testPrivateInject() {
        AnnotationBeanFactory bf = new AnnotationBeanFactory();
        bf.register(new BeanDefinition(NoArgConstructor.class));
        bf.register(new BeanDefinition(TestPrivateInject.class));
        TestPrivateInject o = (TestPrivateInject) new InjectAnnotationBeanConstructor().
                create(bf, "TestPrivateInject", new BeanDefinition(TestPrivateInject.class));
        assertNotNull(o);
        assertNotNull(o.obj);
        assertTrue(o.obj.equals(bf.getBean(NoArgConstructor.class)));
    }

    @Test(expected = ImproperlyConfiguredException.class)
    public void testCyclicDependency() {
        AnnotationBeanFactory bf = new AnnotationBeanFactory();
        bf.register(new BeanDefinition(A.class));
        bf.register(new BeanDefinition(B.class));
        new InjectAnnotationBeanConstructor().create(bf, "", new BeanDefinition(A.class));
    }

    private static class NoArgConstructor {
        @Inject
        public NoArgConstructor() {

        }
    }

    private static class TestPrivateInject {
        NoArgConstructor obj;

        @Inject
        private TestPrivateInject(NoArgConstructor a) {
            obj = a;
        }
    }

    private static class A {
        @Inject
        public A(B b) {

        }
    }

    private static class B {
        @Inject
        public B(A a) {

        }
    }
}