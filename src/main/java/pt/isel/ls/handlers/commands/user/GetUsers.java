package pt.isel.ls.handlers.commands.user;

import pt.isel.ls.exceptions.ParameterException;
import pt.isel.ls.dao.UsersDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.model.User;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class GetUsers implements CommandHandler {

    private TransactionManager tm;
    LinkedList<User> users;
    String skip = null;
    String top = null;
    int count;

    public GetUsers(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * @param commandRequest Using PreparedStatement to select all from user table to a query.
     *                       *Adding each row of query to a List, creating a new Object User
     *                       for each one.
     *                       * @return CommandResult -> List of all users present in DB or
     *                       state message when no users found
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


        if (commandRequest.getParameter() != null) {
            HashMap<String, LinkedList<String>> params = commandRequest.getParameter().getParameters();

            try {
                if (params.containsKey("skip")) {
                    skip = params.get("skip").getFirst();
                }
                if (params.containsKey("top")) {
                    top = params.get("top").getFirst();
                }
            } catch (ParameterException e) {
                throw new ParameterException(e.getMessage());
            }
        }

        tm.doInTransaction(conn -> {

            UsersDao usersDao = new UsersDao(conn);
            users = usersDao.getUsers(skip, top);
            count = usersDao.countUsers();
        });

        CommandResult result;
        if (skip != null) {
            result = new CommandResult(users, count, Integer.parseInt(skip));
        } else {
            result = new CommandResult(users);
        }

        return !users.isEmpty() ? result : new CommandResult("No sport found!");
    }
}
