package ua.abond.lab4.core.jdbc.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.jdbc.exception.CannotGetConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionUtilsTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;

    @Test
    public void testGetConnectionWithAutoCommitFalse() throws Exception {
        when(dataSource.getConnection()).
                thenReturn(connection);
        Connection connection = ConnectionUtils.getConnection(dataSource);
        verify(connection).setAutoCommit(false);
    }

    @Test(expected = CannotGetConnectionException.class)
    public void testFailureWhenCreatingConnection() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        ConnectionUtils.getConnection(dataSource);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerWhenDataSourceNull() {
        ConnectionUtils.getConnection(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCommitWhenConnectionNull() {
        ConnectionUtils.commit(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRollbackWhenConnectionNull() {
        ConnectionUtils.rollback(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCloseConnectionWhenConnectionNull() {
        ConnectionUtils.closeConnection(null);
    }

    @Test
    public void testCommitConnection() throws Exception {
        ConnectionUtils.commit(connection);
        verify(connection).commit();
    }

    @Test
    public void testRollbackConnection() throws Exception {
        ConnectionUtils.rollback(connection);
        verify(connection).rollback();
    }

    @Test
    public void testCloseConnection() throws Exception {
        ConnectionUtils.closeConnection(connection);
        verify(connection).close();
    }

    @Test
    public void testSilentCommit() throws Exception {
        doThrow(new SQLException()).when(connection).commit();
        ConnectionUtils.commit(connection);
    }

    @Test
    public void testSilentRollback() throws Exception {
        doThrow(new SQLException()).when(connection).rollback();
        ConnectionUtils.rollback(connection);
    }

    @Test
    public void testSilentClose() throws Exception {
        doThrow(new SQLException()).when(connection).close();
        ConnectionUtils.closeConnection(connection);
    }
}