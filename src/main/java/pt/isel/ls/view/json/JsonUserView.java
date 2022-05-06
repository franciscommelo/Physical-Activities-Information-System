package pt.isel.ls.view.json;

import pt.isel.ls.model.User;

public class JsonUserView {

    private final int id;
    private final String name;
    private final String email;

    public JsonUserView(User user) {

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }

    public String buildString() {
        return "{ \"id\": "
                + id
                + ", \"name\": \""
                + name
                + "\", \"email\": \""
                + email
                + "\"}";
    }
}
