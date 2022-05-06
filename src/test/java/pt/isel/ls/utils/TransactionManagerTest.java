package pt.isel.ls.utils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerTest {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void doInTransaction(TransactionCallbackTests callback) {

        Connection con = null;

        try {
            con = dataSource.getConnection();
            assert con != null;
            con.setAutoCommit(false);

            callback.execute(con);

            con.commit();
        } catch (SQLException  se) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("\n\n\nERROR!!!\n");
            do {
                System.out.println("SQL STATE: " + se.getSQLState());
                System.out.println("ERROR CODE: " + se.getErrorCode());
                System.out.println("MESSAGE: " + se.getMessage());
                System.out.println();
                se = se.getNextException();
            } while (se != null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
