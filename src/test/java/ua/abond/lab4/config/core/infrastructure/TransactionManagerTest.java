package ua.abond.lab4.config.core.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.Transactional;
import ua.abond.lab4.config.core.bean.BeanDefinition;
import ua.abond.lab4.config.core.bean.TransactionalBeanPostProcessor;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class TransactionManagerTest {
    private AnnotationBeanFactory beanFactory;

    @Before
    public void setUp() {
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

    public static class TestTransactional implements TestInterface {
        @Inject
        private DataSource dataSource;

        @Transactional
        public boolean test() throws SQLException {
            return dataSource.getConnection() == dataSource.getConnection();
        }
    }

    public interface TestInterface {
        boolean test() throws Exception;
    }
}