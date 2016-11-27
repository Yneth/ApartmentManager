package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.ApplicationContext;
import ua.abond.lab4.config.core.BeanFactoryPostProcessor;

public class BeanAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

//    @Override
//    public void postProcess(BeanDefinition beanDefinition, ApplicationContext context) {
//        Arrays.stream(beanDefinition.getType().getMethods()).
//                filter(m -> m.isAnnotationPresent(Bean.class)).
//                map(this::toBeanDefinition);
//
//    }
//
//    private BeanDefinition toBeanDefinition(Method method) {
//        if (method.getParameterCount() > 0) {
//            throw new BeanInstantiationException("Method should not contain arguments. ");
//        }
//        return new BeanDefinition(method.getReturnType());
//    }

    @Override
    public void postProcess(ApplicationContext context) {

    }
}
