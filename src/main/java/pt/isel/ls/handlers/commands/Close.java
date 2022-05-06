package pt.isel.ls.handlers.commands;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.http.HttpPool;

public class Close implements CommandHandler {

    private final HttpPool httpPool;

    public Close(HttpPool httpPool) {
        this.httpPool = httpPool;
    }

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        int port = Integer.parseInt(commandRequest.getParameter().getParameters().get("port").getFirst());

        httpPool.terminate(port);

        return new CommandResult("HTTP Server on port " + port + " terminated!");
    }
}
