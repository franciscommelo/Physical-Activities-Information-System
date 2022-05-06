package pt.isel.ls.handlers.commands.sports;

import pt.isel.ls.dao.SportsDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class PostSports implements CommandHandler {

    private TransactionManager tm;
    private int affectedRows;

    public PostSports(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * @param commandRequest Save the arguments passed in the command line to a HashMap already
     *                       organized by table name and content
     *                       Using a prepared statement and the referred HashMap insert values into DB
     *                       Save the query result to variable.
     * @return CommandResult with a state message indicating if Post command was successful or not
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, LinkedList<String>> args = commandRequest.getParameter().getParameters();
        String name = args.get("name").getFirst();
        String description = args.get("description").getFirst();

        tm.doInTransaction(conn -> {

            affectedRows = new SportsDao(conn).postSports(name, description);
        });

        return affectedRows != 0 ? new CommandResult("Sport created successfully!!")
                : new CommandResult("Error creating a Sport!!");
    }
}
