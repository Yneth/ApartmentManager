package ua.abond.lab4.service.impl;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.postgresql.ds.PGPoolingDataSource;
import ua.abond.lab4.config.core.annotation.Bean;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.ComponentScan;

import javax.sql.DataSource;

@Component
@ComponentScan({"ua.abond.lab4.dao.jdbc", "ua.abond.lab4.service.impl"})
public class DatabaseTestConfig {
    private String url = "jdbc:postgresql://localhost:5432/apartments-test";
    private String driver = "org.postgresql.Driver";
    private String username = "postgres";
    private String password = "123";

    @Bean
    public DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public IDatabaseTester databaseTester() throws ClassNotFoundException {
        return new JdbcDatabaseTester(
                driver,
                url,
                username,
                password
        );
    }
}
