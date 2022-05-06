package pt.isel.ls.handlers.commands.user;

import pt.isel.ls.dao.UsersDao;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.TransactionManager;
import java.util.HashMap;
import java.util.LinkedList;

public class PostUsers implements CommandHandler {


    private TransactionManager tm;
    private int affectedRows = 0;

    public PostUsers(TransactionManager tm) {
        this.tm = tm;
    }

    /**
     * @param commandRequest Getting the arguments passed in cmdLine to a HashMap
     *                       Using PreparedStatement insert into table users the arguments in HashMap saving
     *                       query results
     * @return CommandResult with a state message indicating if Post command was successful or not
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        HashMap<String, LinkedList<String>> args = commandRequest.getParameter().getParameters();

        String name = args.get("name").getFirst();
        String email = args.get("email").getFirst();

        tm.doInTransaction(conn -> {
            affectedRows = new UsersDao(conn).postUsers(name, email);
        });

        return affectedRows != 0 ? new CommandResult("User created successfully!!") :
                new CommandResult("User not created!!");
    }

}
