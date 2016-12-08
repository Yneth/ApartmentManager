package ua.abond.lab4.dao.jdbc;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import java.io.FileOutputStream;

public class JdbcDAOTest {
    private static final String TEST_PACKAGE = "ua.abond.lab4.db";
    private static final String DB_UNIT_DTD_PATH = "dbunit.dtd";

    protected IDataSet dataSet;
    protected IDatabaseTester tester;
    protected BeanFactory beanFactory;

    @Before
    public void setUp() throws Exception {
        beanFactory = new AnnotationBeanFactory(TEST_PACKAGE);
        this.tester = beanFactory.getBean(IDatabaseTester.class);
        onBeforeSetup();

        IDatabaseConnection connection = tester.getConnection();
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream(DB_UNIT_DTD_PATH));
        this.tester.setDataSet(dataSet);
        this.tester.onSetup();
    }

    protected void onBeforeSetup() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        this.tester.onTearDown();
    }

    public static IDataSet loadDataSet(String path) throws Exception {
        return new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(path)
        );
    }
}
