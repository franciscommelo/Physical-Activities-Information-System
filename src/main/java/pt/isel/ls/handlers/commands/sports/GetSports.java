package pt.isel.ls.handlers.commands.sports;

import pt.isel.ls.dao.SportsDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Sport;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;


public class GetSports implements CommandHandler {

    private TransactionManager tm;
    LinkedList<Sport> sports;
    int count;
    String skip = null;
    String top = null;


    public GetSports(TransactionManager tm) {
        this.tm = tm;
    }


    /**
     * @param commandRequest Using PreparedStatement to select all from sport table to a query.
     *                       Adding each row of query to a List, creating a new Object Sport for each one
     * @return CommandResult -> List of all sports present in DB or state message when no sports found.
     */

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


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

            SportsDao sportsDao = new SportsDao(conn);
            sports = sportsDao.getSports(skip, top);
            count = sportsDao.countSports();

        });

        CommandResult result;
        if (skip != null) {
            result = new CommandResult(sports, count, Integer.parseInt(skip));
        } else {
            result = new CommandResult(sports);
        }
        return !sports.isEmpty() ? result : new CommandResult("No sport found!");
    }
}

