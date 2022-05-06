package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetActivitiesBySport implements CommandHandler {

    private LinkedList<Activity> activities;
    private TransactionManager tm;
    String skip = null;
    String top = null;

    public GetActivitiesBySport(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from ACTIVITY the specific sid
     * Adding each row of query to a List, creating a new Object ACTIVITY for each one
     * and pass it to CommandResults
     * Template: GET /sports/{sid}/activities/
     *
     * @param commandRequest requested commmand
     * @return CommandResult
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


        HashMap<String, String> args = commandRequest.getPath().getArgs();
        String sid = args.get("sid");

        if (commandRequest.getParameter() != null) {
            HashMap<String, LinkedList<String>> params = commandRequest.getParameter().getParameters();
            if (params.containsKey("skip")) {
                skip = params.get("skip").getFirst();
            }
            if (params.containsKey("top")) {
                top = params.get("top").getFirst();
            }
        }

        tm.doInTransaction(conn -> {
            activities = new ActivitiesDao(conn).getActivitiesBySport(sid, skip, top);
        });

        return !activities.isEmpty() ? new CommandResult(activities) : new CommandResult("No ativity found!");
    }
}
