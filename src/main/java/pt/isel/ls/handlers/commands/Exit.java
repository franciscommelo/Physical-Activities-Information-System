package pt.isel.ls.handlers.commands;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;

public class  Exit implements CommandHandler {
    /**
     * @param commandRequest requested command
     * @return ExitCmdResult
     * @throws Exception if not found
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        System.out.println("App is being close ...");
        System.exit(0);
        return null;
    }
}
