package pt.isel.ls.view.html;

import pt.isel.ls.model.Activity;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;

public class ActivitiesByIdHtmlView {
    private final Activity activity;

    public ActivitiesByIdHtmlView(Activity activity) {
        this.activity = activity;
    }

    public Element htmlBuild() {

        Element html = new Element("html");
        Element head = new Element("head");
        html.addChild(head);
        Element title = new Element("title");
        head.addChild(title);
        Element body = new Element("body");

        Element titleH1 = new Element("h1");
        titleH1.addChild(new HtmlText("Activity"));
        title.addChild(new HtmlText("Activities Details"));
        new NavigationBarView(body);
        Element list = new Element("ul");

        TableFormat tab = new TableFormat();

        // Add object fields to bullet point list
        new HtmlText("Identifier : " + activity.getId()).addList(list);
        new HtmlText("Date : " + activity.getDate()).addList(list);
        new HtmlText("Duration : " + activity.getDuration()).addList(list);
        new HtmlText("User ID : " + tab.linkFormat("/users/"
                        + activity.getUser().getId(),
                String.valueOf(activity.getUser().getId()))).addList(list);
        new HtmlText("Sport ID : "
                + tab.linkFormat("/sports/" + activity.getSport().getId(),
                String.valueOf(activity.getSport().getId()))).addList(list);
        new HtmlText("Route ID : "
                + tab.linkFormat("/routes/" + activity.getRoutes().getId(),
                String.valueOf(activity.getRoutes().getId()))).addList(list);

        new HtmlText(""
                + tab.linkFormat("/sports/" + activity.getSport().getId() + "/activities",
                "Activity by sport")).addList(list);

        // Add title

        html.addChild(body);
        body.addChild(titleH1);
        body.addChild(list);

        return html;
    }
}
