package pt.isel.ls.view.json;

import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Routes;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;


public class JsonActivityView {

    private int id;
    private final String date;
    private final String duration;
    private final Routes routes;
    private final User user;
    private final Sport sport;

    public JsonActivityView(Activity activity) {
        id = activity.getId();
        date = activity.getDate();
        duration = activity.getDuration();
        routes = activity.getRoutes();
        user = activity.getUser();
        sport = activity.getSport();
    }

    public String buildString() {
        return "{ \"id\": "
                + id
                + ", \"date\": \""
                + date
                + "\", \"duration\": \""
                + duration
                + "\", \"Routes\": "
                + new JsonRouteView(routes).buildString()
                + ", \"User\": "
                + new JsonUserView(user).buildString()
                + ", \"Sport\": "
                + new JsonSportView(sport).buildString()
                + " }";
    }
}
