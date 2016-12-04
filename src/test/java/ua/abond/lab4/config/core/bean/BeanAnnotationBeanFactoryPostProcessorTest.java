package ua.abond.lab4.config.core.bean;

import org.junit.Test;
import ua.abond.lab4.config.core.BeanFactoryPostProcessor;
import ua.abond.lab4.config.core.annotation.Bean;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BeanAnnotationBeanFactoryPostProcessorTest {

    @Test
    public void testSuccessfulBeanAnnotationPostProcessor() throws Exception {
        AnnotationBeanFactory cbf = new AnnotationBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(TestBeanAnnotation.class);
        cbf.register(beanDefinition);

        BeanFactoryPostProcessor bpp = new BeanAnnotationBeanFactoryPostProcessor();
        bpp.postProcess(cbf);

        assertTrue(cbf.containsBeanDefinition(TestBeanAnnotation.class));
        assertTrue(cbf.containsBeanDefinition(String.class));

        String actual = (String)
                cbf.createBean("String", cbf.getBeanDefinition(String.class));
        assertEquals(new TestBeanAnnotation().getString(), actual);
    }

    @Test
    public void testWithArguments() throws Exception {
        AnnotationBeanFactory abf = new AnnotationBeanFactory();
        abf.register(new BeanDefinition(TestBeanAnnotationWithArguments.class));
        new BeanAnnotationBeanFactoryPostProcessor().postProcess(abf);
        assertTrue(abf.containsBeanDefinition(String.class));
        assertTrue(abf.containsBeanDefinition(InputStream.class));
    }

    private static class TestBeanAnnotation {

        @Bean
        public String getString() {
            return "dsadsa";
        }
    }

    private static class TestBeanAnnotationWithArguments {

        @Bean
        public String getString(InputStream is) {
            return "test";
        }

        @Bean
        public InputStream getInputStream() {
            return new ByteArrayInputStream(new byte[]{});
        }
    }
}