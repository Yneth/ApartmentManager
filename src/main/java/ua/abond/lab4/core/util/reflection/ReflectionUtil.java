package ua.abond.lab4.core.util.reflection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static <T> boolean containsMethod(Class<T> type, String name) {
        return Arrays.stream(type.getDeclaredMethods()).
                filter(m -> m.getName().equals(name)).
                findFirst().isPresent();
    }

    public static <T> List<Class<?>> getSuperclasses(Class<T> type) {
        List<Class<?>> result = new ArrayList<>();
        Class<?> superclass = type.getSuperclass();
        while (!Object.class.equals(superclass) && !Objects.isNull(superclass)) {
            result.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return result;
    }

    public static <T> List<Class<?>> getInterfaces(Class<T> type) {
        List<Class<?>> result = new ArrayList<>();
        result.addAll(Arrays.asList(type.getInterfaces()));
        Class<?> superclass = type.getSuperclass();
        while (!Object.class.equals(superclass) && !Objects.isNull(superclass)) {
            result.addAll(Arrays.asList(superclass.getInterfaces()));
            superclass = superclass.getSuperclass();
        }
        return result;
    }

    public static List<Class<?>> getGenericTypeClasses(Class<?> type) {
        List<Class<?>> result = new ArrayList<>();
        Type[] genericInterfaces = type.getGenericInterfaces();
        for (Type t : genericInterfaces) {
            if (!(t instanceof ParameterizedType)) {
                continue;
            }
            ParameterizedType pt = (ParameterizedType) t;
            for (Type typeArgument : pt.getActualTypeArguments()) {
                if (typeArgument instanceof Class) {
                    result.add((Class<?>) typeArgument);
                }
            }
        }
        return result;
    }
}
