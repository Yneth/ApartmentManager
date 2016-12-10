package ua.abond.lab4.config.core.infrastructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.bean.BeanDefinition;
import ua.abond.lab4.config.core.bean.TransactionalBeanPostProcessor;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionManagerTest {
    private AnnotationBeanFactory beanFactory;

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    private TransactionManager transactionManager;

    @Before
    public void setUp() throws Exception {
        transactionManager = new TransactionManager();
        transactionManager.setDataSource(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);

        beanFactory = new AnnotationBeanFactory();
        beanFactory.register(new BeanDefinition(TransactionManager.class));
        beanFactory.register(new BeanDefinition(TransactionalBeanPostProcessor.class));
        beanFactory.register(new BeanDefinition(TestTransactional.class));
        beanFactory.scan("ua.abond.lab4.db");
        beanFactory.refresh();
    }

    @Test
    public void testTransactionManager() throws Exception {
        TestInterface bean = beanFactory.getBean(TestInterface.class);
        assertTrue(bean.test());
    }

    @Test
    public void testNestingOfTransactionMethods() throws Exception {
        TestInterface bean = beanFactory.getBean(TestInterface.class);
        assertTrue(bean.firstMethod());
    }

    @Test
    public void testCreateConnection() throws Exception {
        transactionManager.createConnection();
        verify(dataSource).getConnection();
        verify(connection).setAutoCommit(false);
    }

    @Test
    public void testCloseConnection() throws Exception {
        transactionManager.createConnection();
        transactionManager.releaseConnection();
        verify(connection).close();
    }

    @Test
    public void testCloseConnectionWhenNoOpenConnection() throws Exception {
        transactionManager.releaseConnection();
        verify(connection, never()).close();
    }

    @Test
    public void testCommit() throws Exception {
        transactionManager.createConnection();
        transactionManager.commit();
        verify(connection).commit();
    }

    @Test
    public void testCommitWhenNoOpenConnection() throws Exception {
        transactionManager.commit();
        verify(connection, never()).commit();
    }

    @Test
    public void testRollback() throws Exception {
        transactionManager.createConnection();
        transactionManager.rollback();
        verify(connection).rollback();
    }

    @Test
    public void testRollbackWhenNoOpenConnection() throws Exception {
        transactionManager.rollback();
        verify(connection, never()).rollback();
    }

    public static class TestTransactional implements TestInterface {
        @Inject
        private DataSource dataSource;

        @Override
        @Transactional
        public boolean test() throws SQLException {
            return dataSource.getConnection() == dataSource.getConnection();
        }

        @Override
        @Transactional
        public boolean firstMethod() throws Exception {
            return secondMethod(dataSource.getConnection());
        }

        @Override
        @Transactional
        public boolean secondMethod(Connection connection) throws Exception {
            return connection == dataSource.getConnection();
        }
    }

    public interface TestInterface {
        boolean test() throws Exception;

        boolean firstMethod() throws Exception;

        boolean secondMethod(Connection connection) throws Exception;
    }
}