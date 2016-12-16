package ua.abond.lab4.config;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import ua.abond.lab4.core.annotation.Bean;
import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.ComponentScan;

import javax.sql.DataSource;

@Component
@ComponentScan({"ua.abond.lab4.config"})
public class TestConfig {

    @Bean
    public IDatabaseTester getDatabaseTester(DataSource ds)
            throws Exception {
        IDatabaseTester tester = new DataSourceDatabaseTester(ds);
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        return tester;
    }
}
