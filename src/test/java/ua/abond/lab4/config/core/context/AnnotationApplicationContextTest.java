package ua.abond.lab4.config.core.context;

import org.junit.Test;
import ua.abond.lab4.config.core.ApplicationContext;

import static org.junit.Assert.*;

public class AnnotationApplicationContextTest {

    @Test
    public void testConstructor() {
        ApplicationContext ac = new AnnotationApplicationContext("ua.abond.lab4.config");
        assertTrue(ac.containsBean("DatabaseConfig"));
        assertTrue(ac.containsBean("MVCConfig"));
    }

    @Test
    public void testInjectableConstructor() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext("ua.abond.lab4.config.core.context");
        B bean = ac.getBean(B.class);
        assertNotNull(bean);
        assertEquals(new A().getA(), bean.getA().getA());
    }

    @Test
    public void testBadPackage() {
        assertFalse(new AnnotationApplicationContext("..").containsBean("DataBaseConfig"));
    }

    @Test
    public void testInjectBeanPostProcessor() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext("ua.abond.lab4.config.core.context");
        B bean = ac.getBean(B.class);
        assertNotNull(bean);
        assertEquals(new A().getA(), bean.getA1().getA());
    }

    @Test
    public void testNoArgConstructor() {
        assertFalse(new AnnotationApplicationContext().containsBean("DatabaseConfig"));
    }
}