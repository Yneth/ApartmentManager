package ua.abond.lab4.config;

import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.core.annotation.*;
import ua.abond.lab4.core.jdbc.JdbcTemplate;
import ua.abond.lab4.core.jdbc.TransactionalJdbcTemplate;
import ua.abond.lab4.core.tm.TransactionManager;
import ua.abond.lab4.core.tm.bean.TransactionalBeanPostProcessor;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Optional;

@Component
@Prop("db.properties")
@ComponentScan("ua.abond.lab4.dao.jdbc")
public class DatabaseConfig {
    private static final String JNDI_DATA_SOURCE_NAME = "java:comp/env/jdbc/dataSource";
    private static final Logger logger = Logger.getLogger(DatabaseConfig.class);

    @Value("db.url")
    private String url;
    @Value("db.username")
    private String username;
    @Value("db.password")
    private String password;

    @Bean
    public DataSource getDataSource() throws NamingException {
        DataSource result;
        try {
            Context context = new InitialContext();
            result = (DataSource) context.lookup(JNDI_DATA_SOURCE_NAME);
        } catch (NamingException e) {
            logger.debug("Failed to find DataSource.", e);

            PGPoolingDataSource dataSource = new PGPoolingDataSource();
            dataSource.setUrl(getEnvProperty("JDBC_DATABASE_URL").orElse(url));
            dataSource.setUser(getEnvProperty("JDBC_DATABASE_USERNAME").orElse(username));
            dataSource.setPassword(getEnvProperty("JDBC_DATABASE_PASSWORD").orElse(password));
            dataSource.setMaxConnections(50);
            result = dataSource;
        }
        return result;
    }

    @Bean
    public TransactionalBeanPostProcessor getTransactionalBeanPostProcessor() {
        return new TransactionalBeanPostProcessor();
    }

    @Bean
    public TransactionManager getTransactionManager(DataSource dataSource) {
        return new TransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new TransactionalJdbcTemplate();
    }

    private Optional<String> getEnvProperty(String prop) {
        return Optional.ofNullable(System.getenv().get(prop));
    }
}
