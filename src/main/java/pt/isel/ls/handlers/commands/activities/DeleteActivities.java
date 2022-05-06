package pt.isel.ls.handlers.commands.activities;

import pt.isel.ls.dao.ActivitiesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class DeleteActivities implements CommandHandler {

    TransactionManager tm;

    public DeleteActivities(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from ACTIVITY the specific uid and aid
     * delete each row of query to a List, creating a new Object ACTIVITY for each one
     * and pass it to CommandResult
     *
     * @param commandRequest requested command
     * @return CommandResult
     * @throws Exception if not found
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


        HashMap<String, String> args = commandRequest.getPath().getArgs();
        LinkedList<String> activities = commandRequest.getParameter().getParameters().get("activity");

        tm.doInTransaction(conn -> {


            new ActivitiesDao(conn).deleteActivities(activities, Integer.parseInt(args.get("uid")));
        });


        return new CommandResult("Activities deleted successfully!!");
    }
}





