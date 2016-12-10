package ua.abond.lab4.config;

import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.config.core.annotation.*;
import ua.abond.lab4.config.core.infrastructure.TransactionManager;

import javax.sql.DataSource;

@Component
@Prop("db.properties")
@ComponentScan("ua.abond.lab4.dao.jdbc")
public class DatabaseConfig {
    @Value("db.url")
    private String url;
    @Value("db.driver")
    private String driver;
    @Value("db.username")
    private String username;
    @Value("db.password")
    private String password;

    @Bean
    public DataSource getDataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setInitialConnections(10);
        dataSource.setMaxConnections(50);
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new TransactionManager();
    }
}
