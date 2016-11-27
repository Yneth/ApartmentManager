package ua.abond.lab4.config;

import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.config.core.annotation.Bean;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.ComponentScan;

import javax.sql.DataSource;

@Component
@ComponentScan("ua.abond.lab4.dao.jdbc")
public class DatabaseConfig {
    private String url = "";
    private String driver = "";
    private String username = "";
    private String password = "";

    @Bean
    public DataSource getDataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
