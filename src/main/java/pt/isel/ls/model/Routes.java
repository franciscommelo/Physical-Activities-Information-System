package pt.isel.ls.model;

import java.util.LinkedList;

public class Routes {


    private int id;
    private String startLoc;
    private String endLoc;
    private LinkedList<Sport> sports;

    public int getId() {
        return id;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public String getEndLoc() {
        return endLoc;
    }

    public String getDuration() {
        return duration;
    }

    private String duration;

    /**
     * @param id       -> rid (PK) unique identifier for a route
     * @param startLoc -> Starting Location for the route.
     * @param endLoc   -> Finishing Location for the route
     * @param duration -> Expected duration to finish the route
     */
    public Routes(int id, String startLoc, String endLoc, String duration) {

        this.id = id;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.duration = duration;
    }

    public int getKey() {
        return id;
    }

    /**
     * @return String with all parameters separated by ';'
     */
    @Override
    public String toString() {
        return "id=" + id
                + ";Start Location=" + startLoc
                + ";End Location=" + endLoc
                + ";Duration=" + duration;
    }

    public LinkedList<Sport> getSports() {
        return sports;
    }

    public void setSports(LinkedList<Sport> sports) {
        this.sports = sports;
    }
}
