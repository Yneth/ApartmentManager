package ua.abond.lab4.core.context;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.BeanDefinitionRegistry;
import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.util.reflection.ReflectionUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ClassPathBeanDefinitionScanner {
    private static final Logger logger = Logger.getLogger(ClassPathBeanDefinitionScanner.class);

    private BeanDefinitionRegistry registry;
    private List<Predicate<Class>> predicates;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this(registry, null);
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, List<Predicate<Class>> predicates) {
        this.registry = registry;
        this.predicates = predicates;
        this.registerDefaultPredicates();
    }

    public void scan(String... sourcePackages) throws IOException, ClassNotFoundException {
        Objects.requireNonNull(sourcePackages);

        for (String sourcePackage : sourcePackages) {
            logger.debug("Scanning '" + sourcePackage + "' for components.");
            Arrays.stream(ReflectionUtil.getClasses(sourcePackage)).
                    filter(c -> predicates.stream().
                            allMatch(p -> p.test(c))
                    ).
                    map(BeanDefinition::new).
                    forEach(registry::register);
        }
    }

    private void registerDefaultPredicates() {
        if (predicates == null) {
            predicates = new ArrayList<>();
        }
        predicates.add(c -> !c.isAnnotation() &&
                c.isAnnotationPresent(Component.class) ||
                Arrays.stream(c.getAnnotations()).
                        map(Annotation::annotationType).
                        anyMatch(at -> at.isAnnotationPresent(Component.class))
        );
    }
}
