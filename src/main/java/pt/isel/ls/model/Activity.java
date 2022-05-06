package pt.isel.ls.model;

import java.util.LinkedList;

public class Activity {

    private final int id;
    private final String date;

    private final String duration;

    private final Routes routes;
    private final User user;
    private final Sport sport;

    private LinkedList<Activity> activities = new LinkedList<>();

    /**
     * @param id       -> Aid (PK) unique identification number for the activity
     * @param date     -> Date in which the activity will happen.
     * @param duration -> Duration of the activity
     * @param user     -> uid object type USER (FK)
     * @param routes   -> rid object type ROUTES (FK)
     * @param sport    -> sid object type SPORT (FK)
     */
    public Activity(int id, String date, String duration, User user, Routes routes, Sport sport) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.user = user;
        this.routes = routes;
        this.sport = sport;
    }

    public User getUser() {
        return user;
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
                + ";Date=" + date
                + ";Duration=" + duration
                + ";User=" + user.getKey()
                + ";Route=" + routes.getKey()
                + ";Sport=" + sport.getKey();
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public Routes getRoutes() {
        return routes;
    }

    public Sport getSport() {
        return sport;
    }

    public void setActivities(LinkedList<Activity> a) {
        this.activities = a;
    }

    public LinkedList<Activity> getActivities() {
        return activities;
    }

}
