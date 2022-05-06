package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetActivitiesById implements CommandHandler {

    private TransactionManager tm;
    private LinkedList<Activity> activities = new LinkedList<>();
    Activity activity;

    public GetActivitiesById(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from ACTIVITY the specific sid and aid
     * Adding each row of query to a List, creating a new Object ACTIVITY for each one
     * and pass it to CommandResults
     * Template: GET /sports/{sid}/activities/{aid}
     *
     * @param commandRequest requested command
     * @return CommandResult
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, String> args = commandRequest.getPath().getArgs();
        String aid = args.get("aid");
        String sid = args.get("sid");


        tm.doInTransaction(conn -> {


            activity = new ActivitiesDao(conn).getActivitiesById(aid, sid).getFirst();
            activities = new ActivitiesDao(conn).getActivitiesBySport(args.get("sid"), null, null);

            if (activities != null) {
                activity.setActivities(activities);
            }
        });

        return activity != null ? new CommandResult(activity) : new CommandResult("No activity found!");

    }
}