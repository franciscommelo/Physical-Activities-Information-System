package pt.isel.ls.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.App;
import pt.isel.ls.handlers.commands.user.GetUsers;
import pt.isel.ls.handlers.commands.user.GetUsersById;
import pt.isel.ls.handlers.commands.user.PostUsers;
import pt.isel.ls.model.User;
import pt.isel.ls.router.TransactionManager;
import pt.isel.ls.utils.ConnectionUtils;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;

public class Users {


    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";
    private static DataSource dataSource;
    private static TransactionManager tm;
    private CommandResult cr;
    private CommandRequest cmdRequest;
    private StringBuilder stringBuilder;

    @Before
    public void createUsersTest() {
        tm = new TransactionManager();
        dataSource = App.getDataSource(System.getenv(DATABASE_CONNECTION_ENV));
        tm.setDataSource(dataSource);
        ConnectionUtils.executeQuery("src/main/java/pt/isel/ls/sql/ResetTable.sql");
    }

    @Test
    public void getUsers() throws Exception {
        cmdRequest = new CommandRequest("GET /users");
        cr = new GetUsers(tm).execute(cmdRequest);
        User u1 = new User(1, "Francisco", "francisco1@gmail.com");
        User u2 = new User(2, "Manel", "Man1el@gmail.com");
        User u3 = new User(3, "Jose", "Jose1o1@gmail.com");

        LinkedList<User> ls = new LinkedList<>();
        ls.add(u1);
        ls.add(u2);
        ls.add(u3);
        CommandResult result = new CommandResult(ls);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }

    @Test
    public void postUserAndGetUserById() throws Exception {
        cmdRequest = new CommandRequest("POST /users name=Diogo+Fernandes&email=diogo@isel.pt");
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "Diogo Fernandes");
        map.put("email", "diogo@isel.pt");
        cmdRequest.getPath().setArgs(map);
        cr = new PostUsers(tm).execute(cmdRequest);
        stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------------"
                + "------------------------------------------\n"
                + "\tUser created successfully!!\n"
                + "---------------------------------------------------------------------------");
        Assert.assertEquals(stringBuilder.toString(), cr.getStringBuilder().toString());
        cmdRequest = new CommandRequest("GET /users/4");
        map.clear();
        map.put("uid", "4");
        cmdRequest.getPath().setArgs(map);
        cr = new GetUsersById(tm).execute(cmdRequest);
        User u1 = new User(4, "Diogo Fernandes", "diogo@isel.pt");
        CommandResult result = new CommandResult(u1);
        Assert.assertEquals(result.getStringBuilder().toString(), cr.getStringBuilder().toString());
    }
}
