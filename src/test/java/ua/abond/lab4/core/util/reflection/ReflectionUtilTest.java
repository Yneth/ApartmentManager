package ua.abond.lab4.core.util.reflection;

import org.junit.Test;
import ua.abond.lab4.core.util.reflection.ReflectionUtil;

import java.util.List;

import static org.junit.Assert.*;

public class ReflectionUtilTest {
    @Test
    public void getClasses() throws Exception {

    }

    @Test
    public void testGetSuperclassesWithOneLevel() throws Exception {
        List<java.lang.Class<?>> superclasses =
                ReflectionUtil.getSuperclasses(ParametrizedClassChild.class);
        assertTrue(superclasses.contains(ParametrizedClass.class));
        assertEquals(1, superclasses.size());
    }

    @Test
    public void testGetSuperclassesWithTwoLevels() throws Exception {
        List<java.lang.Class<?>> superclasses =
                ReflectionUtil.getSuperclasses(ParametrizedClassChildChild.class);
        assertTrue(superclasses.contains(ParametrizedClass.class));
        assertTrue(superclasses.contains(ParametrizedClassChild.class));
        assertEquals(2, superclasses.size());
    }

    @Test
    public void testGetSuperclassesOfAFlatClass() throws Exception {
        assertTrue(ReflectionUtil.getSuperclasses(Class.class).isEmpty());
    }

    @Test
    public void testGetInterfacesWhenNoInterfaces() throws Exception {
        assertTrue(ReflectionUtil.getInterfaces(Object.class).isEmpty());
    }

    @Test
    public void testGetInterfaces() throws Exception {
        List<java.lang.Class<?>> interfaces =
                ReflectionUtil.getInterfaces(ParametrizedClass.class);
        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(TestInterface.class));
    }

    @Test
    public void testGetInterfacesFromParent() throws Exception {
        List<java.lang.Class<?>> interfaces =
                ReflectionUtil.getInterfaces(ClassChild.class);
        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(TestInterface.class));
    }

    @Test
    public void testGetGenericTypeClassesWhenGenericParameterSpecified() throws Exception {
        List<java.lang.Class<?>> genericTypeClasses = ReflectionUtil.getGenericTypeClasses(Class.class);
        assertNotNull(genericTypeClasses);
        assertEquals(1, genericTypeClasses.size());
        assertEquals(Integer.class, genericTypeClasses.get(0));
    }

    @Test
    public void testGetGenericTypeClassesWhenGenericParameterNotSpecified() {
        List<java.lang.Class<?>> genericTypeClasses =
                ReflectionUtil.getGenericTypeClasses(ParametrizedClass.class);
        assertTrue(genericTypeClasses.isEmpty());
    }

    private static class ParametrizedClassChild extends ParametrizedClass {

    }

    private static class ParametrizedClassChildChild extends ParametrizedClassChild {

    }

    private static class ClassChild extends Class {

    }

    private static class Class implements TestInterface<Integer> {

    }

    private static class ParametrizedClass<T> implements TestInterface<T> {

    }

    private interface TestInterface<T> {

    }
}