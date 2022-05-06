package pt.isel.ls.handlers.commands.route;

import pt.isel.ls.dao.RoutesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class PostRoutes implements CommandHandler {

    private TransactionManager tm;
    int affectedRows;

    public PostRoutes(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Save the arguments passed in the command line to a HashMap already
     * organized by table name and content
     * Using a prepared statement and the referred HashMap insert values into DB
     * Save the query result to variable.
     * Template: POST /routes startLocation=1place&endLocation=2place&distance=2
     *
     * @param commandRequest requested command
     * @return CommandResult
     */

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, LinkedList<String>> args = commandRequest.getParameter().getParameters();

        String startLocation = args.get("startLocation").getFirst();
        String endLocation = args.get("endLocation").getFirst();
        String distance = args.get("distance").getFirst();

        tm.doInTransaction(conn -> {

            affectedRows = new RoutesDao(conn).postRoutes(startLocation, endLocation, distance);
        });

        return affectedRows != 0 ? new CommandResult("Route created successfully!!") :
                new CommandResult("Route not created!!");

    }
}
