package pt.isel.ls.router;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class Router {
    HashMap<Method, LinkedList<Route>> routesMap = new HashMap<>();
    HashMap<String, String> optionsResult = new HashMap<>();

    public Router() {
        for (Method m : Method.values()) {
            routesMap.put(m, new LinkedList<>());
        }
    }

    /**
     * Add new route to LinkedList routes
     *
     * @param method method for route
     * @param template template for route
     * @param handler handler for route
     */
    public void addRoute(Method method, PathTemplate template, CommandHandler handler, String description) {
        routesMap.get(method).add(new Route(template, handler));
        optionsResult.put(method.toString() + " " + template.getTemplate(), description);
    }

    /**
     * Return RouteResult if find a comandRequest on PathTemplate with ComandHandler and create
     * an HashMap with all values in Path.
     *
     * @param cr requested command
     * @return Optional result
     */
    public Optional<RouteResult> findRoute(CommandRequest cr) throws Exception { //
        Optional<RouteResult> result = Optional.empty();
        LinkedList<Route> routesSearchList = routesMap.get(cr.getMethod());
        for (Route r : routesSearchList) {
            HashMap<String, String> map = r.pathTemplate.match(cr.getPath());
            if (map != null) {
                cr.getPath().setArgs(map);
                return Optional.of(new RouteResult(r.commandHandler));
            }
        }
        if (result.isPresent()) {
            return result;
        } else {
            throw new Exception("Invalid Command!");
        }
    }

    /**
     * @return HashMap optionsResult
     */
    public HashMap<String, String> getRoutesDesc() {
        return optionsResult;
    }
}
