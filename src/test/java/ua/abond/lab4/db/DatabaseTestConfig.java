package ua.abond.lab4.db;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.config.core.annotation.*;

import javax.sql.DataSource;

@Component
@Prop("db.properties")
@ComponentScan({"ua.abond.lab4.dao.jdbc", "ua.abond.lab4.service.impl"})
public class DatabaseTestConfig {
    @Value("db.url")
    private String url;
    @Value("db.driver")
    private String driver;
    @Value("db.username")
    private String username;
    @Value("db.password")
    private String password;

    @Bean
    public DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public IDatabaseTester databaseTester(DataSource ds)
            throws Exception {
        IDatabaseTester tester = new DataSourceDatabaseTester(ds);
        tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        return tester;
    }
}
