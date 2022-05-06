package pt.isel.ls.router;

import pt.isel.ls.handlers.CommandHandler;

public class RouteResult {

    public final CommandHandler cmdHandler;

    public RouteResult(CommandHandler cmdHandler) {
        this.cmdHandler = cmdHandler;
    }
}
