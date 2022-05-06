package pt.isel.ls.router;

import java.util.HashMap;
import java.util.stream.Stream;

public class PathTemplate {
    private final String template;

    public PathTemplate(String template) {
        this.template = template;
    }

    /**
     * get all segments separated by '/'
     *
     * @param route String
     * @return String
     */
    static String[] getValidSegments(String route) {
        return Stream.of(route.split("/"))
                .filter(x -> !x.isBlank())
                .toArray(String[]::new);
    }

    public String getTemplate() {
        return template;
    }

    /**
     * @param path -> Line of cmd
     *             Divides the path and pathtemplate in order to compare them both
     *             Check for variable values identified by "{ }" and compares the rest
     * @return HashMap that contains null if no variables or name and value of the variable values.
     */
    public HashMap<String, String> match(Path path) {

        String[] str = getValidSegments(path.getPath());
        String[] temp = getValidSegments(getTemplate());

        if (temp.length != str.length) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            if (temp[i].startsWith("{")) {
                map.put(temp[i].substring(1, temp[i].length() - 1), str[i]);
                continue;
            }
            if (!str[i].equals(temp[i])) {
                return null;
            }
        }
        return map;
    }
}
