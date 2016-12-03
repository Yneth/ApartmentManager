package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanDefinitionRegistry;
import ua.abond.lab4.config.core.BeanFactoryPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Bean;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BeanAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcess(ConfigurableBeanFactory context) {
        BeanDefinitionRegistry registry = context.getBean(BeanDefinitionRegistry.class);

        context.getBeanDefinitionNames().stream().
                map(context::getBeanDefinition).
                map(BeanDefinition::getType).
                map(Class::getDeclaredMethods).
                flatMap(Arrays::stream).
                filter(method -> method.isAnnotationPresent(Bean.class)).
                map(this::toBeanDefinition).
                forEach(registry::register);
    }

    private BeanDefinition toBeanDefinition(Method method) {
        return new BeanDefinition(method.getReturnType(), method);
    }

}
