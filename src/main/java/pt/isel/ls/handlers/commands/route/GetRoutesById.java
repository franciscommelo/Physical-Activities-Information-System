package pt.isel.ls.handlers.commands.route;

import pt.isel.ls.dao.RoutesDao;
import pt.isel.ls.dao.SportsDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Routes;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;

public class GetRoutesById implements CommandHandler {

    private TransactionManager tm;
    Routes route;


    public GetRoutesById(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * Using PreparedStatement to select from route the specific sid
     * Create a new Object routes with the information from query.
     *
     * @param commandRequest requested command
     * @return CommandResult
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, String> args = commandRequest.getPath().getArgs();

        tm.doInTransaction(conn -> {

            route = new RoutesDao(conn).getRoutesById(args.get("rid"));

            route.setSports(new SportsDao(conn).getSportsByRoute(args.get("rid")));

        });

        return route != null ? new CommandResult(route) : new CommandResult("No route found!");

    }
}
