package pt.isel.ls.handlers.commands.user;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.dao.UsersDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetUsersById implements CommandHandler {

    private TransactionManager tm;
    private LinkedList<Activity> activities = new LinkedList<>();
    private LinkedList<Sport> sports = new LinkedList<>();
    User user;

    public GetUsersById(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * @param commandRequest Getting all variable arguments from cmd line to a HashMap
     *                       Using preparedStatement select from users the user with the
     *                       uid present in the HashMap.
     *                       Create a new object User with the information presented in query
     * @return CommandResult with a object User or a state message when the uid passed doesn't correspond
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, String> args = commandRequest.getPath().getArgs();


        tm.doInTransaction(conn -> {

            user = new UsersDao(conn).getUsersById(args.get("uid"));
            activities = new ActivitiesDao(conn).getActivitiesByUser(args.get("uid"), null, null);
            sports = new UsersDao(conn).getSportsByUser(args.get("uid"));
        });


        if (user != null) {
            if (activities != null) {
                user.setActivities(activities);
            }

            if (sports != null) {
                user.setSports(sports);
            }
        }

        return user != null ? new CommandResult(user) : new CommandResult("No user found!");
    }
}
