package pt.isel.ls.view.json;

import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Routes;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import java.util.LinkedList;

public class JsonView {

    StringBuilder jsonStringResult = new StringBuilder();

    /**
     * Convert result to JSON format
     *
     * @param results Object
     * @return String
     * @throws Exception if not found
     */
    public String convertResultSetToJson(Object results) {

        if (results instanceof LinkedList && ((LinkedList<?>) results).size() > 1) {
            jsonStringResult.append("[");
            for (Object res : (LinkedList) results) {
                addElement(res);
                jsonStringResult.append(",");
            }
            jsonStringResult.deleteCharAt(jsonStringResult.length() - 1);
            jsonStringResult.append("]");
        } else {
            addElement(results);
        }
        return jsonStringResult.toString();
    }


    public void addElement(Object elem) {

        switch (elem.getClass().getSimpleName()) {
            case "Sport":
                jsonStringResult.append(new JsonSportView((Sport) elem).buildString());
                break;
            case "Routes":
                jsonStringResult.append(new JsonRouteView((Routes) elem).buildString());
                break;
            case "User":
                jsonStringResult.append(new JsonUserView((User) elem).buildString());
                break;
            case "Activity":
                jsonStringResult.append(new JsonActivityView((Activity) elem).buildString());
                break;
            default:
                break;
        }
    }
}
