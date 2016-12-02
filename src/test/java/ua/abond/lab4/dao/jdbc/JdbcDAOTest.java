package ua.abond.lab4.dao.jdbc;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class JdbcDAOTest {
    private final String dataSetPath;

    protected IDataSet dataSet;
    protected IDatabaseTester tester;
    protected DataSource dataSource;

    public JdbcDAOTest(String dataSetPath) {
        this.dataSetPath = dataSetPath;
    }

    @Before
    public void setUp() throws Exception {
        this.dataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(dataSetPath)
        );
        Properties prop = getDatabaseProperties();
        this.tester = getDatabaseTester(prop);
        this.tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        this.tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        this.tester.setDataSet(dataSet);
        this.tester.onSetup();
        this.dataSource = getDataSource(prop);
    }

    @After
    public void tearDown() throws Exception {
        this.tester.onTearDown();
    }

    protected IDatabaseTester getDatabaseTester(Properties prop) throws Exception {
        IDatabaseTester tester = new JdbcDatabaseTester(
                prop.getProperty("db.driver"),
                prop.getProperty("db.url"),
                prop.getProperty("db.username"),
                prop.getProperty("db.password")
        );
        return tester;
    }

    protected DataSource getDataSource(Properties prop) {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUser(prop.getProperty("db.username"));
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setPassword(prop.getProperty("db.password"));
        return dataSource;
    }

    protected Properties getDatabaseProperties() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().
                    getContextClassLoader().getResourceAsStream("db.properties")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
