package pt.isel.ls.handlers.commands.sports;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.dao.SportsDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Sport;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetSportsById implements CommandHandler {

    private TransactionManager tm;
    Sport sport;
    private LinkedList<Activity> activities = new LinkedList<>();

    public GetSportsById(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * @param commandRequest Using PreparedStatement to select from sport the specific sid
     *                       Create a new Object Sport with the information from query
     * @return CommandResult -> with a new Object Sport.
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, String> args = commandRequest.getPath().getArgs();

        tm.doInTransaction(conn -> {

            sport = new SportsDao(conn).getSportsById(args.get("sid"));
            activities = new ActivitiesDao(conn).getActivitiesBySport(args.get("sid"), null, null);
        });

        if (!activities.isEmpty()) {
            sport.setActivities(activities);
        }

        return sport != null ? new CommandResult(sport) : new CommandResult("No sport found!");

    }
}
