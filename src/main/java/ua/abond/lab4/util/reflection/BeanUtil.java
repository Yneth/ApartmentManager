package ua.abond.lab4.util.reflection;

import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BeanUtil {

    private BeanUtil() {

    }

    public static Object create(String beanName, BeanCreationCallback callback) {
        try {
            return callback.create();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException("Is " + beanName + " abstract?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Is " + beanName + "'s constructor private?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException("Constructor threw an exception.", e);
        }
    }

    public static Object create(String beanName, Object declaringClass, Method method, Object... args) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        return create(beanName, () -> method.invoke(declaringClass, args));
    }

    public static Object create(String beanName, Constructor<?> constructor, Object... args) {
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        return create(beanName, () -> constructor.newInstance(args));
    }
}
