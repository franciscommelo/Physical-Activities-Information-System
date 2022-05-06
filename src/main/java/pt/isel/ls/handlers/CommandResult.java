package pt.isel.ls.handlers;

import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Routes;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import pt.isel.ls.view.html.*;
import pt.isel.ls.view.json.JsonView;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class CommandResult {

    private StringBuilder strBuilder = new StringBuilder();
    private int skip = 0;
    private String fileName = null;
    private String type = FileType.PLAINTEXT.getText();
    private Object results;
    private int count;

    /**
     * Post result message
     *
     * @param state state of results
     */

    public CommandResult(String state) {
        strBuilder.append("---------------------------------------------------------------------------\n\t"
                + state
                + "\n---------------------------------------------------------------------------"
        );
    }

    /**
     * build a string with all parameters
     *
     * @param results results from request
     */
    public CommandResult(Object results) {
        this.results = results;
    }

    public CommandResult(Object object, int count, int skip) {
        this.skip = skip;
        this.results = object;
        this.count = count;
    }


    /**
     * This method will create all views on Listen
     *
     * @param writer Write values
     */
    public void getResultString(PrintWriter writer) throws IOException {

        boolean isLinkedList = false;

        if (fileName != null) {
            writer = new PrintWriter(new FileWriter(fileName));
        }

        Object result = results;
        if (type.equals(FileType.PLAINTEXT.getText())) {
            if (result != null) {
                writer.write(buildResultString(results).toString());
            }
        } else if (type.equals(FileType.JSON.getText())) {
            writer.write(new JsonView().convertResultSetToJson(results));
        } else {
            if (results instanceof LinkedList) {
                isLinkedList = true;
                result = ((LinkedList<?>) results).getFirst();
            }
            if (result != null) {
                switch (result.getClass().getSimpleName()) {
                    case "User":
                        if (isLinkedList) {
                            new UsersHtmlView(results,
                                    count, skip).htmlBuild(true, true).toHtml(writer);
                        } else {
                            new UsersByIdView((User) result).htmlBuild().toHtml(writer);
                            new SportsHtmlView(((User) results).getSports(),
                                    0, 0).htmlBuild(false, false).toHtml(writer);
                            new ActivitiesHtmlView(((User) results).getActivities(), 0, 0).htmlBuild().toHtml(writer);
                        }
                        break;
                    case "HomeView":
                        new HomeView().htmlBuild().toHtml(writer);
                        break;
                    case "Activity":
                        if (isLinkedList) {
                            new ActivitiesBySportHtml(results, count, skip).htmlBuild(true, true).toHtml(writer);
                        } else {
                            new ActivitiesByIdHtmlView((Activity) result).htmlBuild().toHtml(writer);
                        }
                        break;
                    case "Sport":
                        if (isLinkedList) {
                            new SportsHtmlView(results,
                                    count, skip).htmlBuild(true, true).toHtml(writer);
                        } else {
                            new SportsByIdView((Sport) result).htmlBuild().toHtml(writer);
                        }
                        break;
                    case "Routes":
                        if (isLinkedList) {
                            new RoutesHtmlView(results, count, skip).htmlBuild(true, true).toHtml(writer);
                        } else {
                            new RouteByIdView((Routes) result).htmlBuild().toHtml(writer);
                            new SportsHtmlView(((Routes) results).getSports(),
                                    0, 0).htmlBuild(false, false).toHtml(writer);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public StringBuilder buildResultString(Object results) {
        if (results instanceof LinkedList) {
            for (Object obj : (LinkedList) results) {
                strBuilder.append(obj.toString());
                strBuilder.append("\n");
            }
        } else {
            strBuilder.append(results.toString());
            strBuilder.append("\n");
        }
        return strBuilder;
    }


    /**
     * @return strBuilder
     */
    public StringBuilder getStringBuilder() {
        return strBuilder;
    }

    public void setContentType(String type) {
        this.type = type;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;

    }
}
