package pt.isel.ls.handlers.commands.route;

import pt.isel.ls.dao.RoutesDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.Routes;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;


public class GetRoutes implements CommandHandler {

    private TransactionManager tm;
    String skip = null;
    String top = null;
    int count;

    LinkedList<Routes> routes;

    public GetRoutes(TransactionManager tm) {
        this.tm = tm;
    }


    /**
     * Using PreparedStatement to select all from route table to a query.
     * Adding each row of query to a List, creating a new Object routes for each one.
     *
     * @param commandRequest requested command
     * @return CommandResult
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

            RoutesDao routesDao = new RoutesDao(conn);
            routes = routesDao.getRoutes(skip, top);
            count = routesDao.countRoutes();
        });

        //delete count

        CommandResult result;

        if (skip != null) {
            result = new CommandResult(routes, count, Integer.parseInt(skip));
        } else {
            result = new CommandResult(routes);
        }

        return !routes.isEmpty() ? result : new CommandResult("No route found!");
    }
}

