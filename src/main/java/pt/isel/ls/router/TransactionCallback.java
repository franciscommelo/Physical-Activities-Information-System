package pt.isel.ls.router;

import java.sql.Connection;

/**
 * Execute all handlers Connections
 */
public interface TransactionCallback {
    void execute(Connection conn) throws Exception;
}
