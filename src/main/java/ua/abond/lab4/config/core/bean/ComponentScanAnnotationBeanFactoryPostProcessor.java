package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanFactoryPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.Ordered;
import ua.abond.lab4.config.core.annotation.ComponentScan;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentScanAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    @Override
    public void postProcess(ConfigurableBeanFactory context) {
        AnnotationBeanFactory beanFactory = context.getBean(AnnotationBeanFactory.class);

        List<String> paths = context.getBeanDefinitionNames().stream().
                map(context::getBeanDefinition).
                map(BeanDefinition::getType).
                filter(type -> type.isAnnotationPresent(ComponentScan.class)).
                map(type -> type.getAnnotation(ComponentScan.class)).
                map(ComponentScan::value).
                flatMap(Arrays::stream).
                collect(Collectors.toList());
        String[] pathArray = new String[paths.size()];
        paths.toArray(pathArray);
        beanFactory.scan(pathArray);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
