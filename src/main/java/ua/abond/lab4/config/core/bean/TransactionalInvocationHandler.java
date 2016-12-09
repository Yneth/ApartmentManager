package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class TransactionalInvocationHandler implements InvocationHandler {
    private Object object;
    private TransactionManager tm;

    public TransactionalInvocationHandler(Object object, TransactionManager tm) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(tm);
        this.object = object;
        this.tm = tm;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Exception {
        if (method.isAnnotationPresent(Transactional.class)
                || object.getClass().getMethod(method.getName()).isAnnotationPresent(Transactional.class)) {
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
