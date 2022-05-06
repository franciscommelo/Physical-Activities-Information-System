package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class PostActivities implements CommandHandler {

    private final TransactionManager tm;
    int affectedRows;

    public PostActivities(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Save the arguments passed in the command line to a HashMap already
     * organized by table name and content
     * Using a prepared statement and the referred HashMap insert values into DB
     * Save the query result to variables
     * Template: // POST /sports/1/activities uid=1&duration=20:10:10.130300&date=2010-12-12&rid=1
     *
     * @param commandRequest requested command
     * @return CommandResult
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, LinkedList<String>> args = commandRequest.getParameter().getParameters();
        HashMap<String, String> pathArgs = commandRequest.getPath().getArgs();

        String date = args.get("date").getFirst();
        String duration = args.get("duration").getFirst();
        String uid = args.get("uid").getFirst();
        String sid = pathArgs.get("sid");
        String rid;
        if (args.containsKey("rid")) {
            rid = args.get("rid").getFirst();
        } else {
            rid = null;
        }

        tm.doInTransaction(conn -> {

            affectedRows = new ActivitiesDao(conn).postActivities(date, duration, uid, sid, rid);
        });


        return affectedRows != 0 ? new CommandResult("Activity created successfully!!") :
                new CommandResult("Error creating a Activity!!");
    }

}
