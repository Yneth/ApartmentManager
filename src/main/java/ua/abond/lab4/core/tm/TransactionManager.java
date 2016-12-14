package ua.abond.lab4.core.tm;

import org.apache.log4j.Logger;
import ua.abond.lab4.core.jdbc.util.ConnectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionManager implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(TransactionManager.class);

    private static final String GET_CONNECTION = "getConnection";
    private static final ThreadLocal<Connection> LOCAL_CONNECTION = new ThreadLocal<>();

    private DataSource proxy;
    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.proxy = newInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection conn = LOCAL_CONNECTION.get();
        if (GET_CONNECTION.equals(method.getName()) && conn != null) {
            return conn;
        }
        return method.invoke(dataSource, args);
    }

    public boolean hasBegun() {
        return LOCAL_CONNECTION.get() != null;
    }

    public void begin() {
        logger.debug("Transaction started for thread: " + Thread.currentThread().getName());
        LOCAL_CONNECTION.set(ConnectionUtils.getConnection(dataSource));
    }

    public void commit() {
        logger.debug("Transaction commit for thread: " + Thread.currentThread().getName());
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.commit(connection);
        }
    }

    public void rollback() {
        logger.debug("Transaction rollback for thread: " + Thread.currentThread().getName());
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.rollback(connection);
        }
    }

    public void end() {
        logger.debug("Transaction finished for thread: " + Thread.currentThread().getName());
        Connection connection = LOCAL_CONNECTION.get();
        if (connection != null) {
            ConnectionUtils.closeConnection(connection);
        }
        LOCAL_CONNECTION.remove();
    }

    private DataSource newInstance() {
        return (DataSource) Proxy.newProxyInstance(
                DataSource.class.getClassLoader(),
                new Class[]{DataSource.class},
                this
        );
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.proxy = newInstance();
    }

    public DataSource getDataSourceProxy() {
        return proxy;
    }
}
