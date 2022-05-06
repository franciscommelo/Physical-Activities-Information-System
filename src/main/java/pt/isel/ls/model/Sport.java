package pt.isel.ls.model;

import java.util.LinkedList;

public class Sport {

    private final int id;
    private final String description;
    private final String name;
    private LinkedList<Activity> activities = new LinkedList<>();


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param id          -> sid (PK) unique identifier for the sport
     * @param name        -> Name of the sport.
     * @param description -> Small description of the sport
     */
    public Sport(int id, String name, String description) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setActivities(LinkedList<Activity> a) {
        this.activities = a;
    }

    public LinkedList<Activity> getActivities() {
        return activities;
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
                + ";description=" + description
                + ";name=" + name;
    }
}
