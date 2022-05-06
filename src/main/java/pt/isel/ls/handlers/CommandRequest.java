package pt.isel.ls.handlers;

import pt.isel.ls.router.*;

public class CommandRequest {

    private final Method method;
    private final Path path;
    private Parameters parameter;
    private Header header;
    private Router router;

    /**
     * get Method and path from a String
     *
     * @param cmd Line of commands
     */
    public CommandRequest(String cmd) {
        String[] cmdline = cmd.split(" ");
        method = Method.valueOf(cmdline[0]);
        path = new Path(cmdline[1]);
        if (cmdline.length == 4) {
            header = new Header(cmdline[2]);
            parameter = new Parameters(cmdline[3]);
        } else if (cmdline.length == 3 && cmdline[2].contains("accept")) {
            header = new Header(cmdline[2]);
        } else if (cmdline.length == 3 && !cmdline[2].contains("accept")) {
            parameter = new Parameters(cmdline[2]);
        }
    }


    public CommandRequest(Method method, Path path, Header header, Parameters parameter) {
        this.method = method;
        this.path = path;
        this.header = header;
        this.parameter = parameter;
    }


    /**
     * @return Path
     */
    public Path getPath() {
        return path;
    }

    /**
     * @return Method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return Parameters parameters.
     */
    public Parameters getParameter() {
        return parameter;
    }

    /**
     * @return String parameters
     */
    public Header getHeader() {
        return header;
    }


    public Router getRouter() {
        return router;
    }


    public void setRouter(Router router) {
        this.router = router;
    }
}
