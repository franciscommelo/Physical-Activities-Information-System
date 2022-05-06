package pt.isel.ls.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.App;
import pt.isel.ls.handlers.commands.activities.*;
import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Routes;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import pt.isel.ls.router.TransactionManager;
import pt.isel.ls.utils.ConnectionUtils;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;

public class ActivitiesTest {

    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";
    private static DataSource dataSource;
    private static TransactionManager tm;
    private CommandResult cr;
    private CommandRequest cmdRequest;

    @Before
    public void createRouteTest() {
        tm = new TransactionManager();
        dataSource = App.getDataSource(System.getenv(DATABASE_CONNECTION_ENV));
        tm.setDataSource(dataSource);
        ConnectionUtils.executeQuery("src/main/java/pt/isel/ls/sql/ResetTable.sql");
    }

    @Test
    public void getActivitiesByUser() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        cmdRequest = new CommandRequest("GET /users/1/activities");
        map.clear();
        map.put("uid", "1");
        cmdRequest.getPath().setArgs(map);
        cr = new GetActivitiesByUser(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s1 = new Sport(1,"", "");
        Sport s2 = new Sport(2,"", "");
        Routes r1 = new Routes(1,"", "","");
        Routes r2 = new Routes(2,"", "","");
        Activity a1 = new Activity(1, "2010-12-12", "20:10:10.1303", u1, r1, s1);
        Activity a2 = new Activity(2, "2010-12-13", "20:10:10.1303", u1, r2, s2);
        Activity a3 = new Activity(3, "2010-12-14", "20:10:10.1303", u1, r1, s1);
        LinkedList<Activity> activities = new LinkedList<>();
        activities.add(a1);
        activities.add(a2);
        activities.add(a3);
        CommandResult result = new CommandResult(activities);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void getActivitiesBySports() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        cmdRequest = new CommandRequest("GET /sports/1/activities");
        map.clear();
        map.put("sid", "1");
        cmdRequest.getPath().setArgs(map);
        cr = new GetActivitiesBySport(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s1 = new Sport(1,"", "");
        Routes r1 = new Routes(1,"", "","");
        Activity a1 = new Activity(1, "2010-12-12", "20:10:10.1303", u1, r1, s1);
        Activity a2 = new Activity(3, "2010-12-14", "20:10:10.1303", u1, r1, s1);
        LinkedList<Activity> activities = new LinkedList<>();
        activities.add(a1);
        activities.add(a2);
        CommandResult result = new CommandResult(activities);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }


    @Test
    public void getActivitiesById() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        cmdRequest = new CommandRequest("GET /sports/1/activities/1");
        map.put("sid", "1");
        map.put("aid", "1");
        cmdRequest.getPath().setArgs(map);
        cr = new GetActivitiesById(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s1 = new Sport(1,"", "");
        Routes r1 = new Routes(1,"", "","");
        Activity a1 = new Activity(1, "2010-12-12", "20:10:10.1303", u1, r1, s1);
        CommandResult result = new CommandResult(a1);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void postActivities() throws Exception {
        cmdRequest = new CommandRequest("POST /sports/3/activities "
                + "uid=1&duration=20:10:10.303&date=2010-12-12&rid=1&sid=3");
        HashMap<String, String> mapArgs = new HashMap<>();
        mapArgs.put("sid", "3");
        cmdRequest.getPath().setArgs(mapArgs);
        cr = new PostActivities(tm).execute(cmdRequest);
        Assert.assertEquals("-----------------------------"
                + "----------------------------------------------\n"
                + "\tActivity created successfully!!\n"
                        + "---------------------------------------------------------------------------",
                cr.getStringBuilder().toString());
        cmdRequest = new CommandRequest("GET /sports/3/activities/");
        cmdRequest.getPath().setArgs(mapArgs);
        cr = new GetActivitiesBySport(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s3 = new Sport(3,"", "");
        Routes r1 = new Routes(1,"", "","");
        Activity a1 = new Activity(4, "2010-12-12", "20:10:10.303", u1, r1, s3);
        LinkedList<Activity> activities = new LinkedList<>();
        activities.add(a1);
        CommandResult result = new CommandResult(activities);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void getActivitiesByTopAsc() throws Exception {
        cmdRequest = new CommandRequest("GET /tops/activities sid=1&orderBy=ascending");
        cmdRequest.getParameter().setNewParameters("sid=1&orderBy=ascending");
        cr = new GetActivitiesByTops(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s1 = new Sport(1,"", "");
        Routes r1 = new Routes(1,"", "","");
        Activity a1 = new Activity(1, "2010-12-12", "20:10:10.1303", u1, r1, s1);
        Activity a2 = new Activity(3, "2010-12-14", "20:10:10.1303", u1, r1, s1);
        LinkedList<Activity> activities = new LinkedList<>();
        activities.add(a1);
        activities.add(a2);
        CommandResult result = new CommandResult(activities);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void getActivitiesByTopDesc() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        cmdRequest = new CommandRequest("GET /tops/activities sid=1&orderBy=descending");
        cmdRequest.getParameter().setNewParameters("sid=1&orderBy=descending");
        cmdRequest.getPath().setArgs(map);
        cr = new GetActivitiesByTops(tm).execute(cmdRequest);
        User u1 = new User(1,"", "");
        Sport s1 = new Sport(1,"", "");
        Routes r1 = new Routes(1,"", "","");
        Activity a2 = new Activity(1, "2010-12-12", "20:10:10.1303", u1, r1, s1);
        Activity a1 = new Activity(3, "2010-12-14", "20:10:10.1303", u1, r1, s1);
        LinkedList<Activity> activities = new LinkedList<>();
        activities.add(a2);
        activities.add(a1);
        CommandResult result = new CommandResult(activities);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }
}
