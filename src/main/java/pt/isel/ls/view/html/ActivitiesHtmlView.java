package pt.isel.ls.view.html;

import pt.isel.ls.model.Activity;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;
import java.util.LinkedList;

public class ActivitiesHtmlView {
    LinkedList<Activity> activities = new LinkedList<>();
    private int skip;
    private int count;

    public ActivitiesHtmlView(Object activity, int count, int skip) {
        this.skip = skip;
        this.count = count;
        if (!(activities instanceof LinkedList)) {
            activities.add((Activity) activity);
        } else {
            activities = (LinkedList<Activity>) activity;
        }
    }

    public Element htmlBuild() {
        HtmlText tableStyle = new HtmlText("<style>\n"
                + "table, th, td {\n"
                + "  border: 1px solid black;\n"
                + "  border-collapse: collapse;\n"
                + "}\n"
                + "</style>");
        Element html = new Element("html");
        Element head = new Element("head");
        Element titleH1 = new Element("h1");
        head.addChild(tableStyle);
        titleH1.addChild(new HtmlText("Activity"));
        html.addChild(head);
        Element title = new Element("title");
        title.addChild(new HtmlText("Activity Page"));
        head.addChild(title);
        Element body = new Element("body");
        html.addChild(body);
        body.addChild(titleH1);
        Element table = new Element("table");
        table.setAttribute("style","width:25%");

        Element headers = new Element("tr");
        table.addChild(headers);

        body.addChild(table);

        TableFormat tab = new TableFormat();
        tab.addHeads(headers, "id");
        tab.addHeads(headers, "date");
        tab.addHeads(headers, "duration");

        for (Activity activity : activities) {
            Element row = new Element("tr");
            tab.addRow(row, tab.linkFormat("/sports/"
                    + activity.getSport().getId()
                    + "/activities/"
                    + activity.getId(), String.valueOf(activity.getId())));
            tab.addRow(row, activity.getDate());
            tab.addRow(row, activity.getDuration());
            table.addChild(row);
        }
        return html;
    }
}
