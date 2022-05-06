package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetActivitiesByUser implements CommandHandler {

    private TransactionManager tm;
    String skip = null;
    String top = null;
    LinkedList<Activity> activities;

    public GetActivitiesByUser(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from ACTIVITY the specific uid
     * Adding each row of query to a List, creating a new Object ACTIVITY for each one
     * and pass it to CommandResults
     * Template: GET /users/{uid}/activities/
     *
     * @param commandRequest requested command
     * @return CommandResult
     */

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


        HashMap<String, String> args = commandRequest.getPath().getArgs();

        String uid = args.get("uid");

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
            activities = new ActivitiesDao(conn).getActivitiesByUser(uid, skip, top);
        });


        return !activities.isEmpty() ? new CommandResult(activities) : new CommandResult("No ativity found!");

    }
}

