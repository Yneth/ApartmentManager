package ua.abond.lab4.config.core.context;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.bean.BeanDefinition;
import ua.abond.lab4.config.core.context.test.A;

import java.util.Map;

import static org.junit.Assert.*;

public class ConfigurableBeanFactoryTest {
    private static final String TEST_PACKAGE = "ua.abond.lab4.config.core.context.test";

    private ConfigurableBeanFactory beanFactory;

    @Before
    public void setUp() {
        beanFactory = new AnnotationBeanFactory(TEST_PACKAGE);
    }

    @Test
    public void testBeanDefinitionCount() {
        assertEquals(2, beanFactory.getBeanDefinitionCount());
    }

    @Test
    public void testGetBeansWithAnnotation() {
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Component.class);
        assertNotNull(beansWithAnnotation);
        assertEquals(2, beansWithAnnotation.size());
    }

    @Test
    public void testGetBeanDefinitionsOfType() {
        Map<String, BeanDefinition> beanDefinitionsOfType = beanFactory.getBeanDefinitionsOfType(A.class);
        assertNotNull(beanDefinitionsOfType);
        assertEquals(1, beanDefinitionsOfType.size());
    }

    @Test
    public void testGetBeanDefinitionsOfNonExistingType() {
        Map<String, BeanDefinition> beanDefinitionsOfType = beanFactory.getBeanDefinitionsOfType(Void.class);
        assertNotNull(beanDefinitionsOfType);
        assertEquals(0, beanDefinitionsOfType.size());
    }
}
