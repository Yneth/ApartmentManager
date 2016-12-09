package ua.abond.lab4.config.core.infrastructure;

import ua.abond.lab4.util.jdbc.util.ConnectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionManager implements InvocationHandler {
    private static final String GET_CONNECTION = "getConnection";
    private static final ThreadLocal<Connection> LOCAL_CONNECTION = new ThreadLocal<>();

    private DataSource proxy;
    private DataSource dataSource;

    public TransactionManager() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_CONNECTION.equals(method.getName())) {
            Connection conn = LOCAL_CONNECTION.get();
            if (conn == null) {
                return ConnectionUtils.getConnection(dataSource);
            }
            return conn;
        }
        return method.invoke(dataSource, args);
    }

    public DataSource getDataSource() {
        return proxy;
    }

    public void createConnection() {
        LOCAL_CONNECTION.set(ConnectionUtils.getConnection(dataSource));
    }

    public void commit() {
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.commit(connection);
        }
    }

    public void rollback() {
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.rollback(connection);
        }
    }

    public void releaseConnection() {
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.closeConnection(connection);
            LOCAL_CONNECTION.remove();
        }
    }

    private DataSource newInstance() {
        return (DataSource) Proxy.newProxyInstance(
                DataSource.class.getClassLoader(),
                new Class[]{DataSource.class},
                this
        );
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.proxy = newInstance();
    }

    public DataSource getProxy() {
        return proxy;
    }
}
