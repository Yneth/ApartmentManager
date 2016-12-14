package ua.abond.lab4.core.bean;

import org.junit.Test;
import ua.abond.lab4.config.DatabaseConfig;
import ua.abond.lab4.core.annotation.Bean;
import ua.abond.lab4.core.bean.BeanDefinition;

import javax.sql.DataSource;
import java.util.AbstractList;

import static org.junit.Assert.*;

public class BeanDefinitionTest {

    @Test
    public void testIsAbstractIfInterface() {
        BeanDefinition beanDefinition = new BeanDefinition(DataSource.class);
        assertTrue(beanDefinition.isAbstract());
    }

    @Test
    public void testIsAbstractIfAbstract() {
        BeanDefinition beanDefinition = new BeanDefinition(AbstractList.class);
        assertTrue(beanDefinition.isAbstract());
    }

    @Test(expected = NullPointerException.class)
    public void testTypeConstructorTypeNullArg() {
        new BeanDefinition(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTypeAndMethodConstructorTypeNullArg() {
        new BeanDefinition(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testTypeAndMethodConstructorMethodNullArg() {
        new BeanDefinition(DataSource.class, null);
    }

    @Test
    public void testHasFactoryMethod() throws Exception {
        BeanDefinition beanDefinition = new BeanDefinition(DataSource.class, DatabaseConfig.class.getMethod("getDataSource"));
        assertTrue(beanDefinition.hasFactoryMethod());
    }

    @Test
    public void testHasNoFactoryMethod() throws Exception {
        assertFalse(new BeanDefinition(String.class).hasFactoryMethod());
    }

    @Test
    public void testHasOnlyDefaultConstructorOnInterface() {
        assertTrue(new BeanDefinition(DataSource.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testHasOnlyDefaultConstructorOnAbstract() {
        assertTrue(new BeanDefinition(AbstractList.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testHasDefaultConstructor() {
        assertTrue(new BeanDefinition(ClassWithDefaultConstructor.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testHasDefinedDefaultConstructor() {
        assertTrue(new BeanDefinition(ClassWithDefinedDefaultConstructor.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testHasNoDefaultConstructor() {
        assertFalse(new BeanDefinition(ClassWithParametrizedConstructor.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testHasMultipleConstructors() {
        assertFalse(new BeanDefinition(ClassWithDefaultAndParameterConstructor.class).hasOnlyDefaultConstructor());
    }

    @Test
    public void testClassWithDefaultConstructorAndPublicMethod() {
        ClassWithDefaultConstructorAndPublicMethod a = new ClassWithDefaultConstructorAndPublicMethod();
        assertTrue(new BeanDefinition(ClassWithDefaultConstructorAndPublicMethod.class).hasOnlyDefaultConstructor());
    }

    private static class ClassWithDefaultConstructor {

    }

    private static class ClassWithDefaultConstructorAndPublicMethod {

        @Bean
        public String method() {
            return "";
        }
    }

    private static class ClassWithDefinedDefaultConstructor {
        public ClassWithDefinedDefaultConstructor() {

        }
    }

    private static class ClassWithParametrizedConstructor {
        public ClassWithParametrizedConstructor(int a) {

        }
    }

    private static class ClassWithDefaultAndParameterConstructor {
        public ClassWithDefaultAndParameterConstructor() {

        }

        public ClassWithDefaultAndParameterConstructor(int i) {

        }
    }
}