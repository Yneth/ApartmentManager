package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionalInvocationHandler implements InvocationHandler {
    private Object object;
    private TransactionManager tm;
    private List<Method> transactionalMethods;

    public TransactionalInvocationHandler(Object object, TransactionManager tm) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(transactionalMethods);
        this.object = object;
        this.transactionalMethods = Arrays.stream(object.getClass().getDeclaredMethods()).
                filter(m -> m.isAnnotationPresent(Transactional.class)).
                collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Exception {
        if (transactionalMethods.contains(method)) {
            tm.createConnection();
            try {
                Object obj = method.invoke(object, args);
                tm.commit();
                return obj;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                tm.rollback();
            } finally {
                tm.releaseConnection();
            }
        }
        Object result = null;
        try {
            result = method.invoke(object, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
