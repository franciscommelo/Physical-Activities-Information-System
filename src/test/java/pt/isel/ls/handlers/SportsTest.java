package pt.isel.ls.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.App;
import pt.isel.ls.handlers.commands.sports.GetSports;
import pt.isel.ls.handlers.commands.sports.GetSportsById;
import pt.isel.ls.handlers.commands.sports.PostSports;
import pt.isel.ls.model.Sport;
import pt.isel.ls.router.TransactionManager;
import pt.isel.ls.utils.ConnectionUtils;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;

public class SportsTest {

    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";
    private static DataSource dataSource;
    private static TransactionManager tm;
    private CommandResult cr;
    private CommandResult testResult;
    private CommandRequest cmdRequest;
    private StringBuilder stringBuilder;

    @Before
    public void createSportsTest() {
        tm = new TransactionManager();
        dataSource = App.getDataSource(System.getenv(DATABASE_CONNECTION_ENV));
        tm.setDataSource(dataSource);
        ConnectionUtils.executeQuery("src/main/java/pt/isel/ls/sql/ResetTable.sql");
    }

    @Test
    public void getSports() throws Exception {
        cmdRequest = new CommandRequest("GET /sports");
        cr = new GetSports(tm).execute(cmdRequest);
        Sport s1 = new Sport(1, "corrida", "corrida desc");
        Sport s2 = new Sport(2, "futebol", "futebol desc");
        Sport s3 = new Sport(3, "tenis", "tenis desc");
        LinkedList<Sport> sp = new LinkedList<>();
        sp.add(s1);
        sp.add(s2);
        sp.add(s3);
        testResult = new CommandResult(sp);
        Assert.assertEquals(testResult.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void postSportAndGetByID() throws Exception {
        cmdRequest = new CommandRequest("POST /sports name=Voleibol&description=voleibol+desc");
        cmdRequest.getParameter().setNewParameters("name=Voleibol&description=voleibol+desc");
        cr = new PostSports(tm).execute(cmdRequest);
        stringBuilder = new StringBuilder();
        stringBuilder.append("------------------------"
                + "---------------------------------------------------\n"
                + "\tSport created successfully!!\n"
                + "---------------------------------------------------------------------------");
        Assert.assertEquals(stringBuilder.toString(), cr.getStringBuilder().toString());
        cmdRequest = new CommandRequest("GET /sports/4");
        HashMap<String, String> map = new HashMap<>();
        map.put("sid", "4");
        cmdRequest.getPath().setArgs(map);
        cr = new GetSportsById(tm).execute(cmdRequest);
        Sport s4 = new Sport(4, "Voleibol", "voleibol desc");
        testResult = new CommandResult(s4);
        Assert.assertEquals(testResult.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }
}
