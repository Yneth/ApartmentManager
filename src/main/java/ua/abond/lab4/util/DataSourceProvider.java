package ua.abond.lab4.util;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {
    private static final DataSourceProvider INSTANCE = new DataSourceProvider();
    private final DataSource dataSource;

    private DataSourceProvider() {
        this.dataSource = createDataSource();
    }

    private DataSource createDataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("apartments-test");
        dataSource.setUser("postgres");
        dataSource.setPassword("123");
        return dataSource;
    }

    public static DataSourceProvider getInstance() {
        return INSTANCE;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
