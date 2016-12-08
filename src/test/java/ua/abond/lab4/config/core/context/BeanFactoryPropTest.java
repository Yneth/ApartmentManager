package ua.abond.lab4.config.core.context;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.prop.PropTestClass;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryPropTest {
    private static final String TEST_PACKAGE = "ua.abond.lab4.config.core.context.prop";
    private static final String TEST_PROP_FILE = "prop.test.properties";

    private BeanFactory beanFactory;
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        beanFactory = new AnnotationBeanFactory(TEST_PACKAGE);
        properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream(TEST_PROP_FILE));
    }

    @Test
    public void testIfFieldIsSet() {
        PropTestClass bean = beanFactory.getBean(PropTestClass.class);
        assertNotNull(bean.getString());
        assertEquals(properties.get("test"), bean.getString());
    }
}
