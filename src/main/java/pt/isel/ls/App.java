package pt.isel.ls;


import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.handlers.commands.*;
import pt.isel.ls.handlers.commands.activities.*;
import pt.isel.ls.handlers.commands.route.GetRoutes;
import pt.isel.ls.handlers.commands.route.GetRoutesById;
import pt.isel.ls.handlers.commands.route.PostRoutes;
import pt.isel.ls.handlers.commands.sports.GetSports;
import pt.isel.ls.handlers.commands.sports.GetSportsById;
import pt.isel.ls.handlers.commands.sports.PostSports;
import pt.isel.ls.handlers.commands.user.GetUsers;
import pt.isel.ls.handlers.commands.user.GetUsersById;
import pt.isel.ls.handlers.commands.user.PostUsers;
import pt.isel.ls.http.HttpPool;
import pt.isel.ls.router.*;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static final String DATABASE_CONNECTION_ENV = "JDBC_DATABASE_URL";
    private static DataSource dataSource = null;
    private static final TransactionManager tm = new TransactionManager();
    private static final HttpPool httpPool = new HttpPool();

    public static void main(String[] args) {

        try {
            dataSource = App.getDataSource(System.getenv(DATABASE_CONNECTION_ENV));
        } catch (Exception e) {
            System.err.println("ERROR> You need to set " + DATABASE_CONNECTION_ENV + " environment variable");
            System.exit(1);
        }

        Router r = new Router();
        tm.setDataSource(dataSource);
        addCommand(r);

        if (args.length >= 2) {
            createCommandRequest(String.join(" ", args), r);
        } else {
            Scanner sc = new Scanner(System.in);
            for (; ; ) {
                System.out.print("cmd > ");
                if (sc.hasNext()) {
                    String cmd = sc.nextLine();
                    int cmdSize = cmd.split("\\s+").length;
                    if (cmdSize > 4 || cmdSize < 2) {
                        System.out.println("Invalid Command Structure !!");
                        System.out.println("Command Structure > {method} {path} {parameters}");
                    } else {
                        createCommandRequest(cmd, r);
                    }
                }
            }
        }
    }

    /**
     * Create new commandRequest
     *
     * @param cmd - String with the command
     * @param r   - router
     */
    public static void createCommandRequest(String cmd, Router r) {

        try {
            CommandRequest cr = new CommandRequest(cmd);
            cr.setRouter(r);
            processInput(r, cr);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Execute handler
     *
     * @param r  - Router
     * @param cr - CommandRequest
     */
    public static void processInput(Router r, CommandRequest cr) throws Exception {
        Optional<RouteResult> rs = r.findRoute(cr);
        if (rs.isPresent()) {

            CommandHandler ch = rs.get().cmdHandler;
            CommandResult commandResult = ch.execute(cr);

            if (cr.getHeader() != null) {
                HashMap<String, String> headers = cr.getHeader().getHeaders();
                String type = headers.get("accept");
                commandResult.setContentType(type);
                if (headers.containsKey("file-name")) {
                    commandResult.setFileName(headers.get("file-name"));
                }
            }
            PrintWriter result = new PrintWriter(System.out, true);
            commandResult.getResultString(result);
            result.println();
        }
    }


    /**
     * Create list with all PathTemplates
     */
    private static void addCommand(Router r) {

        //User Management
        r.addRoute(Method.GET, new PathTemplate("/users"), new GetUsers(tm),
                "Presents all users");
        r.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUsersById(tm),
                "Present user identified by uid");
        r.addRoute(Method.POST, new PathTemplate("/users"), new PostUsers(tm),
                "Create a new user");

        //Route Management
        r.addRoute(Method.GET, new PathTemplate("/routes"), new GetRoutes(tm),
                "Presents all routes");
        r.addRoute(Method.GET, new PathTemplate("/routes/{rid}"), new GetRoutesById(tm),
                "Present route identified by rid");
        r.addRoute(Method.POST, new PathTemplate("/routes"), new PostRoutes(tm),
                "Create a new route");

        //Sports Management
        r.addRoute(Method.POST, new PathTemplate("/sports"), new PostSports(tm),
                "Create a new sport");
        r.addRoute(Method.GET, new PathTemplate("/sports"), new GetSports(tm),
                "Presents all sports");
        r.addRoute(Method.GET, new PathTemplate("/sports/{sid}"), new GetSportsById(tm),
                "Present sport identified by sid");

        // Activities Management
        r.addRoute(Method.POST, new PathTemplate("/sports/{sid}/activities"), new PostActivities(tm),
                "Creates a new activity in sport identified by sid");
        r.addRoute(Method.GET, new PathTemplate("/sports/{sid}/activities"), new GetActivitiesBySport(tm),
                "Presents all activities in sport identified by sid");
        r.addRoute(Method.GET, new PathTemplate("/sports/{sid}/activities/{aid}"), new GetActivitiesById(tm),
                "Present activity identified by aid in sport identified by sid");
        r.addRoute(Method.GET, new PathTemplate("/users/{uid}/activities"), new GetActivitiesByUser(tm),
                "Presents activities done by user identified by uid");
        r.addRoute(Method.GET, new PathTemplate("/tops/activities"), new GetActivitiesByTops(tm),
                "Presents activities sorted by duration or date");
        r.addRoute(Method.DELETE, new PathTemplate("/users/{uid}/activities"), new DeleteActivities(tm),
                "Delete activities associated with user uid");

        //OtherCommands
        r.addRoute(Method.EXIT, new PathTemplate("/"), new Exit(),
                "Exits the program");
        r.addRoute(Method.LISTEN, new PathTemplate("/"), new Listen(r, httpPool),
                "Enable the web server");
        r.addRoute(Method.OPTION, new PathTemplate("/"), new Option(),
                "Presents all available commands");
        r.addRoute(Method.GET, new PathTemplate("/"), new Home(),
                "Home template");
        r.addRoute(Method.CLOSE, new PathTemplate("/"), new Close(httpPool),
                "Close web App");
    }

    /**
     * Get environment variables
     *
     * @param connectionUrl URL
     * @return DataSource
     */
    public static DataSource getDataSource(String connectionUrl) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(connectionUrl);
        return dataSource;
    }
}