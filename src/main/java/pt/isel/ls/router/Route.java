package pt.isel.ls.router;

import pt.isel.ls.handlers.CommandHandler;

public class Route {
    public final PathTemplate pathTemplate;
    public final CommandHandler commandHandler;

    public Route(PathTemplate pathTemplate, CommandHandler commandHandler) {
        this.pathTemplate = pathTemplate;
        this.commandHandler = commandHandler;
    }
}
