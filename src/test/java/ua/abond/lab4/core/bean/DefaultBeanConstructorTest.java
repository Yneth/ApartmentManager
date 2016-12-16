package ua.abond.lab4.core.bean;

import org.junit.Test;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.bean.DefaultBeanConstructor;
import ua.abond.lab4.core.context.AnnotationBeanFactory;
import ua.abond.lab4.core.exception.BeanInstantiationException;

import javax.sql.DataSource;
import java.util.AbstractList;

import static org.junit.Assert.*;

public class DefaultBeanConstructorTest {

    @Test
    public void testCanCreateNestedClassWithDefault() {
        assertTrue(canCreateIgnoreFactoryAndName(new BeanDefinition(TestNestedClassWithDefaultConstructor.class)));
    }

    @Test
    public void testCanCreateClassWithPrivateConstructor() {
        assertTrue(canCreateIgnoreFactoryAndName(new BeanDefinition(TestClassWithPrivateConstructor.class)));
    }

    @Test
    public void testCanCreateClassWithDefaultConstructor() {
        assertTrue(canCreateIgnoreFactoryAndName(new BeanDefinition(TestClass.class)));
    }

    @Test
    public void testCanCreateClassWithPublicConstructor() {
        assertTrue(canCreateIgnoreFactoryAndName(new BeanDefinition(TestClassWithException.class)));
    }

    @Test
    public void testCreateDefaultConstructor() {
        assertNotNull(createBeanIgnoreFactoryAndName(new BeanDefinition(TestClass.class)));
    }

    @Test
    public void testCreatePrivateConstructor() {
        assertNotNull(createBeanIgnoreFactoryAndName(new BeanDefinition(TestClassWithPrivateConstructor.class)));
    }

    @Test(expected = BeanInstantiationException.class)
    public void testInvocationTargetException() {
        createBeanIgnoreFactoryAndName(new BeanDefinition(TestClassWithException.class));
    }

    @Test(expected = BeanInstantiationException.class)
    public void testInstantiationExceptionForAbstractClass() {
        createBeanIgnoreFactoryAndName(new BeanDefinition(AbstractList.class));
    }

    @Test(expected = BeanInstantiationException.class)
    public void testInstantiationExceptionForInterface() {
        createBeanIgnoreFactoryAndName(new BeanDefinition(DataSource.class));
    }

    private boolean canCreateIgnoreFactoryAndName(BeanDefinition bd) {
        return new DefaultBeanConstructor().canCreate(new AnnotationBeanFactory(), "", bd);
    }

    private Object createBeanIgnoreFactoryAndName(BeanDefinition bd) {
        return new DefaultBeanConstructor().create(new AnnotationBeanFactory(), "", bd);
    }

    private static class TestNestedClassWithDefaultConstructor {

    }

    private static class TestClassWithPrivateConstructor {
        private TestClassWithPrivateConstructor() {

        }
    }

    private static class TestClassWithException {
        public TestClassWithException() {
            throw new RuntimeException();
        }
    }

    private static class TestClass {

    }
}