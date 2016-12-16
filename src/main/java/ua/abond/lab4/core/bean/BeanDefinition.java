package ua.abond.lab4.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class BeanDefinition {
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

    public boolean isNestedClass() {
        return Modifier.isStatic(type.getModifiers()) && type.getEnclosingClass() != null;
    }

    public boolean isInnerClass() {
        return !Modifier.isStatic(type.getModifiers()) && type.getEnclosingClass() != null;
    }

    public boolean hasOnlyDefaultConstructor() {
        if (hasFactoryMethod()) {
            return factoryMethod.getParameterCount() == 0;
        }
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (isNestedClass() && constructors.length == 2) {
            return Arrays.stream(constructors).
                    map(Constructor::getParameterTypes).
                    flatMap(Arrays::stream).
                    allMatch(Class::isSynthetic);
        }
        return constructors.length <= 1
                && (constructors.length == 0 || constructors[0].getParameterCount() == 0);
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BeanDefinition))
            return false;
        BeanDefinition that = (BeanDefinition) o;
        return Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
