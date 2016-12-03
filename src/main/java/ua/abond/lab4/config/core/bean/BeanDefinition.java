package ua.abond.lab4.config.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class BeanDefinition implements Comparable<BeanDefinition> {
    private final Class<?> type;

    private Class<?> declaringClass;
    private Method factoryMethod;

    public BeanDefinition(Class<?> type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public BeanDefinition(Class<?> type, Method factoryMethod) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(factoryMethod);
        this.type = type;
        this.factoryMethod = factoryMethod;
        this.declaringClass = factoryMethod.getDeclaringClass();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(type.getModifiers());
    }

    public boolean hasOnlyDefaultConstructor() {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length > 1) {
            return false;
        }
        return constructors.length == 0
                || constructors[0].getParameterCount() == 0
                || (hasFactoryMethod() && factoryMethod.getParameterCount() == 0);
    }

    public boolean hasFactoryMethod() {
        return factoryMethod != null;
    }

    public Method getFactoryMethod() {
        return factoryMethod;
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "type=" + type +
                '}';
    }

    @Override
    public int compareTo(BeanDefinition that) {
        if (this.hasOnlyDefaultConstructor() && that.hasOnlyDefaultConstructor()) {
            return 0;
        } else if (this.hasOnlyDefaultConstructor()) {
            return 1;
        }
        return -1;
    }
}
