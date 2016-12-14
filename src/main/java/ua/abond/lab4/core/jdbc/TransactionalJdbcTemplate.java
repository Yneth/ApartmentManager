package ua.abond.lab4.core.jdbc;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.tm.TransactionManager;

public class TransactionalJdbcTemplate extends JdbcTemplate {
    @Inject
    private TransactionManager transactionManager;

    @Override
    public void beginTransaction() {
        transactionManager.begin();
    }

    @Override
    public void endTransaction() {
        transactionManager.end();
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
