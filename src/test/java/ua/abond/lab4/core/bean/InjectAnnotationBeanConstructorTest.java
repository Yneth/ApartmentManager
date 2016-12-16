package ua.abond.lab4.core.bean;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.core.BeanConstructor;
import ua.abond.lab4.core.annotation.Bean;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.bean.BeanAnnotationBeanFactoryPostProcessor;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.bean.InjectAnnotationBeanConstructor;
import ua.abond.lab4.core.context.AnnotationBeanFactory;
import ua.abond.lab4.core.exception.BeanInstantiationException;
import ua.abond.lab4.core.exception.ImproperlyConfiguredException;

import static org.junit.Assert.*;

public class InjectAnnotationBeanConstructorTest {
    private BeanConstructor bc;
    private AnnotationBeanFactory bf;

    @Before
    public void setUp() {
        bf = new AnnotationBeanFactory();
        bc = new InjectAnnotationBeanConstructor();
    }

    @Test(expected = BeanInstantiationException.class)
    public void testCreateAbstract() {
        BeanDefinition bd = new BeanDefinition(Abstract.class);
        bf.register(bd);
        bc.create(bf, "", bd);
    }

    @Test
    public void testCanNotCreateInnerClass() {
        BeanDefinition bd = new BeanDefinition(InnerClass.class);
        bf.register(bd);
        assertFalse(bc.canCreate(bf, "", bd));
    }

    @Test
    public void testCanCreateNoInjectableConstructor() {
        BeanDefinition bd = new BeanDefinition(NoInjectableConstructor.class);
        bf.register(bd);
        assertFalse(bc.canCreate(bf, "", bd));
    }

    @Test(expected = BeanInstantiationException.class)
    public void testCreateNoInjectableConstructor() {
        BeanDefinition bd = new BeanDefinition(NoInjectableConstructor.class);
        bf.register(bd);
        bc.create(bf, "", bd);
    }

    @Test(expected = ImproperlyConfiguredException.class)
    public void testHasMoreThanOneInjectableConstructor() {
        BeanDefinition bd = new BeanDefinition(TwoInjectConstructors.class);
        bf.register(bd);
        bc.canCreate(bf, "", bd);
    }

    @Test
    public void testCanCreateFactoryMethodBeanWithArgs() {
        BeanDefinition bd = new BeanDefinition(FactoryMethodBeanWithArgs.class);
        BeanDefinition nacBd = new BeanDefinition(NoArgConstructor.class);
        bf.register(bd);
        bf.register(nacBd);
        new BeanAnnotationBeanFactoryPostProcessor().postProcess(bf);

        BeanDefinition stringBd = bf.getBeanDefinition(String.class);
        assertTrue(bc.canCreate(bf, "", stringBd));
    }

    @Test
    public void testCanNotCreateFactoryMethodBeanWithArgs() {
        BeanDefinition bd = new BeanDefinition(FactoryMethodBeanWithArgs.class);
        BeanDefinition nacBd = new BeanDefinition(NoInjectableConstructor.class);
        bf.register(bd);
        bf.register(nacBd);
        new BeanAnnotationBeanFactoryPostProcessor().postProcess(bf);

        BeanDefinition stringBd = bf.getBeanDefinition(String.class);
        assertFalse(bc.canCreate(bf, "", stringBd));
    }

    @Test
    public void testCreateFactoryMethodBeanWithArgs() {
        BeanDefinition bd = new BeanDefinition(FactoryMethodBeanWithArgs.class);
        BeanDefinition nacBd = new BeanDefinition(NoArgConstructor.class);
        bf.register(bd);
        bf.register(nacBd);
        new BeanAnnotationBeanFactoryPostProcessor().postProcess(bf);

        String o = (String) bc.create(bf, "String", bf.getBeanDefinition(String.class));
        assertNotNull(o);
        assertEquals(new FactoryMethodBeanWithArgs().getString(new NoArgConstructor()), o);
    }

    @Test(expected = BeanInstantiationException.class)
    public void testNoAvailableConstructor() {
        BeanDefinition bd = new BeanDefinition(String.class);
        bf.register(bd);
        bc.create(bf, "", bd);
    }

    @Test
    public void testPrivateInject() {
        BeanDefinition noArgBean = new BeanDefinition(NoArgConstructor.class);
        bf.register(noArgBean);
        BeanDefinition privateInjectBean = new BeanDefinition(TestPrivateInject.class);
        bf.register(privateInjectBean);
        TestPrivateInject o = (TestPrivateInject) bc.
                create(bf, "TestPrivateInject", privateInjectBean);
        assertNotNull(o);
        assertNotNull(o.obj);
        assertTrue(o.obj.equals(bf.getBean(NoArgConstructor.class)));
    }

    @Test(expected = ImproperlyConfiguredException.class)
    public void testCyclicDependency() {
        BeanDefinition aBd = new BeanDefinition(A.class);
        bf.register(aBd);
        bf.register(new BeanDefinition(B.class));
        bc.create(bf, "A", aBd);
    }

    private class InnerClass {

        @Inject
        public InnerClass() {

        }
    }

    private static abstract class Abstract {

        @Inject
        public Abstract() {

        }
    }

    private static class NoInjectableConstructor {

    }

    private static class FactoryMethodBeanWithArgs {

        @Inject
        public FactoryMethodBeanWithArgs() {

        }

        @Bean
        public String getString(NoArgConstructor nac) {
            return nac.getClass().getSimpleName();
        }
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

    private static class TwoInjectConstructors {

        @Inject
        private TwoInjectConstructors(NoArgConstructor a) {

        }

        @Inject
        private TwoInjectConstructors(String s) {

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