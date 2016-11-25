package ua.abond.lab4.dao.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TestConnectionProxy implements InvocationHandler {
    private Connection connection;

    public TestConnectionProxy(Connection connection) {
        this.connection = connection;
    }

    public static Connection newInstance(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                new TestConnectionProxy(connection)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            if (method.getName().equals("commit")) {
                return result;
            }
            result = method.invoke(connection, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected invocation exception: " +
                    e.getMessage());
        }
        return result;
    }
}
