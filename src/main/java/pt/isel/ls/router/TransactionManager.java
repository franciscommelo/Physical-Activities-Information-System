package pt.isel.ls.router;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private DataSource dataSource;
    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL"; // default value

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(System.getenv(DATABASE_CONNECTION_ENV));
        return dataSource;
    }

    /**
     * Open connection with DB, commit transaction, close connection and return Excetpions if exist.
     *
     * @param callback interface callback
     */
    public void doInTransaction(TransactionCallback callback) throws Exception {

        Connection con = null;

        try {
            if (dataSource == null) {
                con = getDataSource().getConnection();
            } else {
                con = dataSource.getConnection();
            }
            con.setAutoCommit(false);
            callback.execute(con);
            con.commit();
        } catch (Exception e) {
            try {
                con.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception e1) {
                throw new Exception(e1.getMessage());
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    throw new SQLException(e2.getMessage());
                }
            }
        }
    }
}
