package ua.abond.lab4.util.jdbc;

import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.tm.TransactionManager;

public class TransactionalJdbcTemplate extends JdbcTemplate {
    @Inject
    private TransactionManager transactionManager;

    @Override
    public void beginTransaction() {
    }

    @Override
    public void endTransaction() {
    }

    @Override
    public boolean isManaged() {
        return transactionManager.hasBegun();
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.dataSource = transactionManager.getDataSourceProxy();
    }
}
