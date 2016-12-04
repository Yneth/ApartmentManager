package ua.abond.lab4.config.core.infrastructure;

import ua.abond.lab4.util.jdbc.util.ConnectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionManager implements InvocationHandler {
    private static final String GET_CONNECTION = "getConnection";

    private final DataSource proxy;
    private final DataSource dataSource;
    private final ThreadLocal<Connection> localConnection;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.proxy = newInstance();
        this.localConnection = new ThreadLocal<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (GET_CONNECTION.equals(method.getName())) {
            Connection conn = localConnection.get();
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
        localConnection.set(ConnectionUtils.getConnection(dataSource));
    }

    public void commit() {
        Connection connection = localConnection.get();
        if (connection != null) {
            ConnectionUtils.commit(connection);
        }
    }

    public void rollback() {
        Connection connection = localConnection.get();
        if (connection != null) {
            ConnectionUtils.rollback(connection);
        }
    }

    public void releaseConnection() {
        Connection connection = localConnection.get();
        if (connection != null) {
            ConnectionUtils.closeConnection(connection);
            localConnection.remove();
        }
    }

    private DataSource newInstance() {
        return (DataSource) Proxy.newProxyInstance(
                DataSource.class.getClassLoader(),
                new Class[]{DataSource.class},
                this
        );
    }
}
