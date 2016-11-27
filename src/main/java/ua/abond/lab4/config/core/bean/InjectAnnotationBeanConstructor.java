package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.ApplicationContext;
import ua.abond.lab4.config.core.BeanConstructor;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectAnnotationBeanConstructor implements BeanConstructor {

    @Override
    public boolean canCreate(ApplicationContext context, String bean, BeanDefinition beanDefinition) {
        List<Constructor<?>> constructors = getInjectableConstructors(beanDefinition);

        if (constructors.size() > 1) {
//            throw new BeanInstantiationException(bean + ". There is only one injectable constructor available.");
            return false;
        }
        if (constructors.isEmpty()) {
            return true;
        }
        Constructor ctr = constructors.get(0);
        return Arrays.stream(ctr.getParameterTypes()).allMatch(cls -> context.getBean(cls) != null);
    }


    @Override
    public Object create(ApplicationContext context, String bean, BeanDefinition beanDefinition) {
        Constructor<?> constructor = getInjectableConstructorsStream(beanDefinition).
                findFirst().orElseThrow(() -> new BeanInstantiationException("No constructor available."));
        Object[] args = Arrays.stream(constructor.getParameterTypes()).
                map(context::getBean).collect(Collectors.toList()).toArray();
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new BeanInstantiationException("Is " + bean + " abstract?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Are " + bean + "'s constructors private?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException("Unknown exception.", e);
        }
    }

    private List<Constructor<?>> getInjectableConstructors(BeanDefinition beanDefinition) {
        return getInjectableConstructorsStream(beanDefinition).
                collect(Collectors.toList());
    }

    private Stream<? extends Constructor<?>> getInjectableConstructorsStream(BeanDefinition beanDefinition) {
        return Arrays.stream(beanDefinition.getType().getConstructors()).
                filter(c -> c.isAnnotationPresent(Inject.class));
    }
}
