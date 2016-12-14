package ua.abond.lab4.core.tm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.context.AnnotationBeanFactory;
import ua.abond.lab4.core.tm.TransactionManager;
import ua.abond.lab4.core.tm.bean.TransactionalBeanPostProcessor;

import javax.sql.DataSource;
import java.sql.Connection;

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
        transactionManager = new TransactionManager(dataSource);
        transactionManager.setDataSource(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);

        beanFactory = new AnnotationBeanFactory();
        beanFactory.register(new BeanDefinition(TransactionManager.class));
        beanFactory.register(new BeanDefinition(TransactionalBeanPostProcessor.class));
        beanFactory.scan("ua.abond.lab4.db");
        beanFactory.refresh();
    }

    @After
    public void cleanUp() {
        transactionManager.end();
    }

    @Test
    public void testCreateConnection() throws Exception {
        transactionManager.begin();
        verify(dataSource).getConnection();
        verify(connection).setAutoCommit(false);
    }

    @Test
    public void testCloseConnection() throws Exception {
        transactionManager.begin();
        transactionManager.end();
        verify(connection).close();
    }

    @Test
    public void testCloseConnectionWhenNoOpenConnection() throws Exception {
        transactionManager.end();
        verify(connection, never()).close();
    }

    @Test
    public void testCommit() throws Exception {
        transactionManager.begin();
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
        transactionManager.begin();
        transactionManager.rollback();
        verify(connection).rollback();
    }

    @Test
    public void testRollbackWhenNoOpenConnection() throws Exception {
        transactionManager.rollback();
        verify(connection, never()).rollback();
    }
}