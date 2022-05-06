package pt.isel.ls.handlers.commands;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.http.HttpPool;
import pt.isel.ls.http.HttpServerApplication;
import pt.isel.ls.router.Router;

public class Listen implements CommandHandler {

    private Router router;
    private static final int DEFAULT_LISTEN_PORT = 8080;
    int port;
    private final HttpPool httpPool;

    public Listen(Router router, HttpPool httpPool) {
        this.router = router;
        this.httpPool = httpPool;
    }

    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {


        if (commandRequest.getParameter() != null) {
            port = Integer.parseInt(commandRequest.getParameter().getParameters().get("port").getFirst());
        } else {
            port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : DEFAULT_LISTEN_PORT;
        }


        if (isValidPort(port)) {
            new HttpServerApplication(port, router, httpPool).run();
        } else {
            return new CommandResult("Webserver not started");
        }

        return new CommandResult("New Http server created. PORT:" + port);

    }

    // valid ports should be between 1-65535
    private boolean isValidPort(int port) {
        return (port >= 1 && port <= 65535);
    }


}
