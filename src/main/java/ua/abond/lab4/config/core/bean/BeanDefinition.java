package ua.abond.lab4.config.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class BeanDefinition implements Comparable<BeanDefinition> {
    private final Class<?> type;

    public BeanDefinition(Class<?> type) {
        this.type = type;
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(type.getModifiers());
    }

    public boolean isInterface() {
        return Modifier.isInterface(type.getModifiers());
    }

    public boolean hasOnlyDefaultConstructor() {
        Constructor<?>[] constructors = type.getConstructors();
        if (constructors.length > 1) {
            return false;
        }
        return constructors.length == 0 || constructors[0].getParameterCount() == 0;
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
