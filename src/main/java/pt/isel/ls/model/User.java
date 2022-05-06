package pt.isel.ls.model;

import java.util.LinkedList;

public class User {

    private final int id;
    private final String name;

    private final String email;
    private LinkedList<Activity> activities = new LinkedList<>();
    private LinkedList<Sport> sports = new LinkedList<>();

    /**
     * @param id    -> uid (PK) unique identifier for the user
     * @param name  -> Name of the user.
     * @param email -> Email of the user
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getKey() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * @return String with all parameters separated by ';'
     */
    @Override
    public String toString() {
        return "id=" + id
                + ";name=" + name
                + ";email=" + email;
    }

    public void setActivities(LinkedList<Activity> a) {
        this.activities = a;
    }

    public LinkedList<Activity> getActivities() {
        return activities;
    }

    public void setSports(LinkedList<Sport> a) {
        this.sports = a;
    }

    public LinkedList<Sport> getSports() {
        return sports;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
