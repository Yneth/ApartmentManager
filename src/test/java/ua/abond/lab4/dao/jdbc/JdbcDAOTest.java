package ua.abond.lab4.dao.jdbc;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdWriter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

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

        Writer out = new OutputStreamWriter(new FileOutputStream(
                DB_UNIT_DTD_PATH
        ));
        FlatDtdWriter datasetWriter = new FlatDtdWriter(out);
        datasetWriter.write(dataSet);
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
