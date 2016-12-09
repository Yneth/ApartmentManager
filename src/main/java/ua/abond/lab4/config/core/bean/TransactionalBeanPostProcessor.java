package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.Ordered;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TransactionalBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        if (TransactionManager.class.isAssignableFrom(bean.getClass())) {
            return bean;
        }
        BeanDefinition bd = new BeanDefinition(TransactionManager.class);
        TransactionManager tm = (TransactionManager)
                factory.createBean(TransactionManager.class.getSimpleName(), bd);
        if (DataSource.class.isAssignableFrom(bean.getClass())) {
            tm.setDataSource((DataSource) bean);
            return tm.getProxy();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(ConfigurableBeanFactory factory, Object bean, String simpleName) {
        TransactionManager tm = factory.getBean(TransactionManager.class);

        Object result = bean;
        if (containsTransactionalMethods(bean)) {
            result = createProxy(bean, tm);
        }
        return result;
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
