package pt.isel.ls.utils;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.io.FileReader;
import java.util.Scanner;

public class ConnectionUtils {


    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";

    static TransactionManagerTest tm = new TransactionManagerTest();

    public static void executeQuery(String filePath) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(System.getenv(DATABASE_CONNECTION_ENV));
        tm.setDataSource(dataSource);

        tm.doInTransaction(conn -> {
            Scanner s = new Scanner(new FileReader(filePath));
            s.useDelimiter(";");
            while (s.hasNextLine()) {
                conn.prepareStatement(s.nextLine()).execute();
            }
        });
    }


    public static DataSource getDataSource(String connectionUrl) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(connectionUrl);
        return dataSource;
    }
}
