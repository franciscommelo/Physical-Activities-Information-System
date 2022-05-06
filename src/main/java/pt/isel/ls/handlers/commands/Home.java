package pt.isel.ls.handlers.commands;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.view.html.HomeView;

public class Home implements CommandHandler {


    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {
        return new CommandResult(new HomeView());
    }
}
