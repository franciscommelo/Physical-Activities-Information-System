package pt.isel.ls.utils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionCallbackTests {
    void execute(Connection conn) throws SQLException, FileNotFoundException;
}
