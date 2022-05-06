package pt.isel.ls.view.json;

import pt.isel.ls.model.Sport;

public class JsonSportView {

    private final int id;
    private final String description;
    private final String name;

    public JsonSportView(Sport sport) {
        id = sport.getId();
        description = sport.getDescription();
        name = sport.getName();
    }


    public String buildString() {
        return "{ \"id\": "
                + id
                + ", \"name\": \""
                + name
                + "\", \"description\": \""
                + description + "\"}";
    }
}
