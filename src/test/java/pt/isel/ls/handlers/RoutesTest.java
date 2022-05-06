package pt.isel.ls.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.App;
import pt.isel.ls.handlers.commands.route.GetRoutes;
import pt.isel.ls.handlers.commands.route.GetRoutesById;
import pt.isel.ls.handlers.commands.route.PostRoutes;
import pt.isel.ls.model.Routes;
import pt.isel.ls.router.TransactionManager;
import pt.isel.ls.utils.ConnectionUtils;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;

public class RoutesTest {

    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";
    private static DataSource dataSource;
    private static TransactionManager tm;
    private CommandResult cr;
    private CommandRequest cmdRequest;
    private StringBuilder stringBuilder;

    @Before
    public void createRouteTest() {
        tm = new TransactionManager();
        dataSource = App.getDataSource(System.getenv(DATABASE_CONNECTION_ENV));
        tm.setDataSource(dataSource);
        ConnectionUtils.executeQuery("src/main/java/pt/isel/ls/sql/ResetTable.sql");
    }

    @Test
    public void getRoutes() throws Exception {
        cmdRequest = new CommandRequest("GET /routes");
        cr = new GetRoutes(tm).execute(cmdRequest);
        Routes r1 = new Routes(1, "Location 1", "Location 2", "12");
        Routes r2 = new Routes(2, "Location 10", "Location 11", "21");
        Routes r3 = new Routes(3, "Location 20", "Location 21", "41");
        LinkedList<Routes> ls = new LinkedList<>();
        ls.add(r1);
        ls.add(r2);
        ls.add(r3);
        CommandResult result = new CommandResult(ls);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void postRouteAndGetRouteById() throws Exception {
        cmdRequest = new CommandRequest("POST /routes startLocation=3&endLocation=4&distance=20");
        HashMap<String, String> map = new HashMap<>();
        map.put("startLocation", "3");
        map.put("endLocation", "4");
        map.put("distance", "20");
        cmdRequest.getPath().setArgs(map);
        cr = new PostRoutes(tm).execute(cmdRequest);
        stringBuilder = new StringBuilder();
        stringBuilder.append("-----------------------------"
                + "----------------------------------------------\n"
                + "\tRoute created successfully!!\n"
                + "---------------------------------------------------------------------------");
        Assert.assertEquals(stringBuilder.toString(), cr.getStringBuilder().toString());
        cmdRequest = new CommandRequest("GET /routes/4");
        map.clear();
        map.put("rid", "4");
        cmdRequest.getPath().setArgs(map);
        cr = new GetRoutesById(tm).execute(cmdRequest);
        Routes r1 = new Routes(4, "3", "4", "20");
        CommandResult result = new CommandResult(r1);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }
}
