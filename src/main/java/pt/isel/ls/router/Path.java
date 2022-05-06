package pt.isel.ls.router;

import java.util.HashMap;


public class Path {

    private final String path;

    private HashMap<String, String> args;

    /**
     * @param path path from cmd Line
     */
    public Path(String path) {
        this.path = path;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }


    /**
     * Replace args
     *
     * @param args arguments from commandLine
     */
    public void setArgs(HashMap<String, String> args) {
        this.args = args;
    }

    /**
     * @return args
     */
    public HashMap<String, String> getArgs() {
        return args;
    }

    public void putOneArg(String key, String value) {
        if (args == null) {
            args = new HashMap<>();
        }
        args.put(key, value);
    }
}
