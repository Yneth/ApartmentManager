package ua.abond.lab4.config.core.bean;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.Ordered;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.exception.BeanInstantiationException;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered {
    private static final Logger logger = Logger.getLogger(InjectAnnotationBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        Arrays.stream(bean.getClass().getDeclaredFields()).
                filter(f -> f.isAnnotationPresent(Inject.class)).
                forEach(f -> inject(factory, bean, f));
        return bean;
    }

    private void inject(ConfigurableBeanFactory factory, Object bean, Field f) {
        logger.debug("Trying to inject " + f.getName() + " of type " + f.getType()
                + " to '" + bean.getClass().getSimpleName() + "'");

        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
        Object injectable;
        if (!factory.containsBean(f.getType())) {
            BeanDefinition bd = factory.getBeanDefinition(f.getType());
            injectable = factory.createBean(bd.getType().getSimpleName(), bd);
        } else {
            injectable = factory.getBean(f.getType());
        }
        try {
            f.set(bean, injectable);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException("Failed to inject " + injectable + " to " + bean);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
