package pt.isel.ls.view.json;

import pt.isel.ls.model.Routes;

public class JsonRouteView {

    private int id;
    private String startLoc;
    private String endLoc;
    private String duration;


    public JsonRouteView(Routes route) {
        id = route.getId();
        startLoc = route.getStartLoc();
        endLoc = route.getEndLoc();
        duration = route.getDuration();
    }


    public String buildString() {
        return "{ \"id\": "
                + id
                + ", \"startLocation\": \""
                + startLoc
                + "\", \"endLocation\": \""
                + endLoc
                + "\", \"duration\": \""
                + duration
                + "\"}";
    }
}
