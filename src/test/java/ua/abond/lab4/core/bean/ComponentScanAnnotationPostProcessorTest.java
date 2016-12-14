package ua.abond.lab4.core.bean;

import org.junit.Test;
import ua.abond.lab4.core.annotation.ComponentScan;
import ua.abond.lab4.core.bean.componentscan.TestClass;
import ua.abond.lab4.core.context.AnnotationBeanFactory;

import static org.junit.Assert.*;

public class ComponentScanAnnotationPostProcessorTest {

    @Test
    public void test() {
        AnnotationBeanFactory abf = new AnnotationBeanFactory();
        abf.register(new BeanDefinition(TestComponent.class));
        new ComponentScanAnnotationBeanFactoryPostProcessor().postProcess(abf);
        assertTrue(abf.containsBeanDefinition(TestClass.class));
    }

    @ComponentScan("ua.abond.lab4.config.core.bean.componentscan")
    private static class TestComponent {

    }
}