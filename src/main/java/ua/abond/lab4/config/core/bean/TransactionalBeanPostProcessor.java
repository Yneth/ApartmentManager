package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TransactionalBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        TransactionManager tm = factory.getBean(TransactionManager.class);
//        factory.getBeansOfType(Object.class).values().stream().
//                filter(this::containsTransactionalMethod).
//                forEach(b -> factory.createBean(beanName, createProxy(bean, bean.getClass(), tm)));

        return null;
    }

    private boolean containsTransactionalMethod(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredMethods()).
                anyMatch(m -> m.isAnnotationPresent(Transactional.class));
    }

    private Object createProxy(Object obj, Class<?> type, TransactionManager tm) {
        return Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new TransactionalInvocationHandler(obj, tm)
        );
    }
}
