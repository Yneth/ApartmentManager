package ua.abond.lab4.util.reflection;

import java.lang.reflect.InvocationTargetException;

public interface BeanCreationCallback {
    Object create() throws InstantiationException, IllegalAccessException, InvocationTargetException;
}
