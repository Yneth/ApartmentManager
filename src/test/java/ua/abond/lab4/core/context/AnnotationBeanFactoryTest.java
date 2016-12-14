package ua.abond.lab4.core.context;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.core.BeanFactory;
import ua.abond.lab4.core.context.test.A;
import ua.abond.lab4.core.context.test.B;
import ua.abond.lab4.core.exception.NoSuchBeanException;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.Assert.*;

public class AnnotationBeanFactoryTest {
    private static final String TEST_PACKAGE = "ua.abond.lab4.config.core.context.test";

    private BeanFactory beanFactory;

    @Before
    public void setUp() {
        beanFactory = new AnnotationBeanFactory(TEST_PACKAGE);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullArg() {
        new AnnotationBeanFactory(null);
    }

    @Test
    public void testContainsBean() {
        assertTrue(beanFactory.containsBean("A"));
        assertTrue(beanFactory.containsBean("B"));
    }

    @Test
    public void testGetBeanByName() {
        assertNotNull(beanFactory.getBean("A"));
        assertNotNull(beanFactory.getBean("B"));
    }

    @Test
    public void testGetBeanByType() {
        assertNotNull(beanFactory.getBean(A.class));
        assertNotNull(beanFactory.getBean(B.class));
    }

    @Test(expected = NoSuchBeanException.class)
    public void testGetBeanNonExisting() {
        beanFactory.getBean("tetsts");
    }

    @Test(expected = NullPointerException.class)
    public void testGetBeanByNameWithNullArg() {
        beanFactory.getBean((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetBeanByTypeWithNullArg() {
        beanFactory.getBean((Class) null);
    }

    @Test
    public void testGetBeanByNameAndGetBeanByTypeShouldReturnSameReference() {
        assertTrue(beanFactory.getBean("A") == beanFactory.getBean(A.class));
        assertTrue(beanFactory.getBean("B") == beanFactory.getBean(B.class));
    }

    @Test
    public void testGetBeanByNameShouldReturnSameReference() {
        Object a = beanFactory.getBean("A");
        assertTrue(a == beanFactory.getBean("A"));
        assertTrue(a == beanFactory.getBean("A"));
        assertTrue(a == beanFactory.getBean("A"));
    }

    @Test
    public void testGetBeanByTypeShouldReturnSameReference() {
        Object a = beanFactory.getBean(A.class);
        assertTrue(a == beanFactory.getBean(A.class));
        assertTrue(a == beanFactory.getBean(A.class));
        assertTrue(a == beanFactory.getBean(A.class));
    }

    @Test
    public void testGetBeansOfType() {
        Map<String, A> beansOfTypeA = beanFactory.getBeansOfType(A.class);
        assertNotNull(beansOfTypeA);
        assertFalse(beansOfTypeA.isEmpty());
        assertEquals(1, beansOfTypeA.size());

        Map<String, B> beansOfTypeB = beanFactory.getBeansOfType(B.class);
        assertNotNull(beansOfTypeB);
        assertFalse(beansOfTypeB.isEmpty());
        assertEquals(1, beansOfTypeB.size());
    }

    @Test
    public void testGetNonExistingBeansOfType() {
        Map<String, DataSource> beansOfType = beanFactory.getBeansOfType(DataSource.class);
        assertNotNull(beansOfType);
        assertTrue(beansOfType.isEmpty());
    }

    @Test
    public void testInjectableConstructor() {
        AnnotationBeanFactory ac = new AnnotationBeanFactory(TEST_PACKAGE);
        B bean = ac.getBean(B.class);
        assertNotNull(bean);
        assertEquals(new A().getA(), bean.getA().getA());
    }

    @Test
    public void testInjectBeanPostProcessor() {
        AnnotationBeanFactory ac = new AnnotationBeanFactory(TEST_PACKAGE);
        B bean = ac.getBean(B.class);
        assertNotNull(bean);
        assertEquals(new A().getA(), bean.getA1().getA());
    }
}