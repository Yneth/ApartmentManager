package ua.abond.lab4.core.tm.bean;

import ua.abond.lab4.core.BeanPostProcessor;
import ua.abond.lab4.core.ConfigurableBeanFactory;
import ua.abond.lab4.core.Ordered;
import ua.abond.lab4.core.annotation.Transactional;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.tm.TransactionManager;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TransactionalBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(ConfigurableBeanFactory factory, Object bean, String simpleName) {
        Object result = bean;
        if (containsTransactionalMethods(bean)) {
            TransactionManager tm = getTransactionManager(factory);
            result = createProxy(bean, tm);
        }
        return result;
    }

    private TransactionManager getTransactionManager(ConfigurableBeanFactory factory) {
        Class<? extends TransactionManager> type = TransactionManager.class;
        if (!factory.containsBean(type)) {
            BeanDefinition bd = factory.getBeanDefinition(type);
            return (TransactionManager) factory.createBean(type.getSimpleName(), bd);
        } else {
            return factory.getBean(type);
        }
    }

    private boolean containsTransactionalMethods(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredMethods()).
                anyMatch(m -> m.isAnnotationPresent(Transactional.class));
    }

    private Object createProxy(Object obj, TransactionManager tm) {
        Class<?> type = obj.getClass();
        return Proxy.newProxyInstance(
                type.getClassLoader(),
                type.getInterfaces(),
                new TransactionalInvocationHandler(obj, tm)
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
