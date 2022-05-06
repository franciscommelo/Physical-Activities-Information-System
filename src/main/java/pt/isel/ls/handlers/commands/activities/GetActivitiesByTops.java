package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetActivitiesByTops implements CommandHandler {
    private final TransactionManager tm;
    LinkedList<Activity> activities;

    public GetActivitiesByTops(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from ACTIVITY the specific sid and other parameters, ordered by duration
     * Adding each row of query to a List, creating a new Object ACTIVITY for each one
     * and pass it to CommandResults
     * Template: GET /tops/activities sid=1&orderBy=ascending&distance=11
     * GET /tops/activities accept:text/plain sid=1&orderBy=ascending&distance=11
     *
     * @param commandRequest requested command
     * @return CommandResult
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        activities = new LinkedList<>();
        HashMap<String, LinkedList<String>> args = commandRequest.getParameter().getParameters();

        String sid = args.containsKey("sid") ? args.get("sid").getFirst() : null;
        String distance = args.containsKey("distance") ? args.get("distance").getFirst() : null;
        String date = args.containsKey("date") ? args.get("date").getFirst() : null;
        String rid = args.containsKey("rid") ? args.get("rid").getFirst() : null;
        String orderbyParam = args.containsKey("orderBy") ? args.get("orderBy").getFirst() : null;


        tm.doInTransaction(conn -> {
            activities = new ActivitiesDao(conn).getActivitiesByTops(sid, distance, date, rid, orderbyParam);
        });


        return !activities.isEmpty() ? new CommandResult(activities) : new CommandResult("No ativity found!");

    }
}

