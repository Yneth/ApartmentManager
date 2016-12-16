package ua.abond.lab4.core.tm.bean;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.annotation.Transactional;
import ua.abond.lab4.core.tm.TransactionManager;
import ua.abond.lab4.core.tm.exception.TransactionException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class TransactionalInvocationHandler implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(TransactionalInvocationHandler.class);

    private Object object;
    private TransactionManager tm;

    public TransactionalInvocationHandler(Object object, TransactionManager tm) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(tm);
        this.object = object;
        this.tm = tm;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isTransactional = isTransactional(method) || hasTransactional(method.getName());
        Consumer<Runnable> callback = r -> {
            if (isTransactional) r.run();
        };
        callback.accept(() -> tm.begin());
        try {
            Object obj = method.invoke(object, args);
            callback.accept(() -> tm.commit());
            return obj;
        } catch (IllegalAccessException e) {
            callback.accept(() -> tm.rollback());
            throw new TransactionException("Tried to invoke inaccessible method '" + method.getName() + "'", e);
        } catch (InvocationTargetException e) {
            callback.accept(() -> tm.rollback());
            logger.debug("Method threw an exception.", e);
            throw e.getCause();
        } finally {
            callback.accept(() -> tm.end());
        }
    }

    private boolean hasTransactional(String methodName) {
        return Arrays.stream(object.getClass().getMethods()).
                filter(m -> m.getName().equals(methodName)).
                filter(this::isTransactional).
                findFirst().
                map(m -> true).
                orElse(false);
    }

    private boolean isTransactional(Method method) {
        return method.isAnnotationPresent(Transactional.class);
    }
}
