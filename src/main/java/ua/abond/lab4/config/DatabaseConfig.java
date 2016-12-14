package ua.abond.lab4.config;

import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.config.core.annotation.*;
import ua.abond.lab4.config.core.tm.TransactionManager;
import ua.abond.lab4.config.core.tm.bean.TransactionalBeanPostProcessor;
import ua.abond.lab4.util.jdbc.JdbcTemplate;
import ua.abond.lab4.util.jdbc.TransactionalJdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

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
        dataSource.setUrl(getEnvProperty("JDBC_DATABASE_URL").orElse(url));
        dataSource.setUser(getEnvProperty("JDBC_DATABASE_USERNAME").orElse(username));
        dataSource.setPassword(getEnvProperty("JDBC_DATABASE_PASSWORD").orElse(password));
        dataSource.setInitialConnections(10);
        dataSource.setMaxConnections(50);
        return dataSource;
    }

    @Bean
    public TransactionalBeanPostProcessor getTransactionalBeanPostProcessor() {
        return new TransactionalBeanPostProcessor();
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        TransactionManager tm = new TransactionManager(dataSource);
        return tm;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new TransactionalJdbcTemplate();
    }

    private Optional<String> getEnvProperty(String prop) {
        return Optional.ofNullable(System.getenv().get(prop));
    }
}
