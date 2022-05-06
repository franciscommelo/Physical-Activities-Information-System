package pt.isel.ls.view.html;

import pt.isel.ls.model.Routes;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;
import java.util.LinkedList;

public class RoutesHtmlView {

    private int skip;
    private int count;

    LinkedList<Routes> routes = new LinkedList<>();


    public RoutesHtmlView(Object route, int count, int skip) {
        this.skip = skip;
        this.count = count;
        if (!(route instanceof LinkedList)) {
            routes.add((Routes) route);
        } else {
            routes = (LinkedList<Routes>) route;
        }
    }

    public Element htmlBuild(boolean withPaging, boolean withNavegationBar) {

        Element html = new Element("html");
        Element head = new Element("head");
        html.addChild(head);
        Element body = new Element("body");
        if (withNavegationBar) {
            new NavigationBarView(body);
        }


        HtmlText tableStyle = new HtmlText("<style>\n"
                + "table, th, td {\n"
                + "  border: 1px solid black;\n"
                + "  border-collapse: collapse;\n"
                + "}\n"
                + "</style>");
        head.addChild(tableStyle);
        Element titleH1 = new Element("h1");
        titleH1.addChild(new HtmlText("ROUTES"));
        Element title = new Element("title");
        title.addChild(new HtmlText("Routes Page"));
        head.addChild(title);
        html.addChild(body);
        body.addChild(titleH1);
        Element table = new Element("table");
        table.setAttribute("style", "width:25%");

        Element headers = new Element("tr");
        table.addChild(headers);

        body.addChild(table);

        TableFormat tab = new TableFormat();
        tab.addHeads(headers, "id");
        tab.addHeads(headers, "duration");
        tab.addHeads(headers, "start loc");
        tab.addHeads(headers, "end loc");

        for (Routes route : routes) {

            Element row = new Element("tr");

            tab.addRow(row, tab.linkFormat("/routes/"
                    + route.getId(), String.valueOf(route.getId())));
            tab.addRow(row, route.getDuration());
            tab.addRow(row, route.getStartLoc());
            tab.addRow(row, route.getEndLoc());

            table.addChild(row);
        }
        if (withPaging) {
            Element paging = new Element("p");
            body.addChild(paging);
            new HtmlText().pagination(skip, count, "routes", paging);
        }

        Element createUserH1 = new Element("h1");
        body.addChild(createUserH1);
        createUserH1.addChild(new HtmlText("Create new Route"));

        Element labelDuration = new Element("label");
        labelDuration.addChild(new HtmlText("Duration:"));
        Element inputDuration = new Element("input");
        inputDuration.setAttribute("id", "duration");
        inputDuration.setAttribute("name", "duration");


        Element labelStartLoc = new Element("label");
        labelStartLoc.addChild(new HtmlText("Start location: "));
        Element inputStartLoc = new Element("input");
        inputStartLoc.setAttribute("id", "startloc");
        inputStartLoc.setAttribute("name", "startloc");


        Element labelEndLoc = new Element("label");
        labelEndLoc.addChild(new HtmlText("End location: "));
        Element inputEndLoc = new Element("input");
        inputEndLoc.setAttribute("id", "endloc");
        inputEndLoc.setAttribute("name", "endloc");


        Element submit = new Element("input");
        submit.setAttribute("type", "submit");
        submit.setAttribute("value", "Submit");

        Element form = new Element("form");
        form.addChild(labelDuration);
        form.addChild(inputDuration);
        form.addChild(new Element("br"));
        form.addChild(labelStartLoc);
        form.addChild(inputStartLoc);
        form.addChild(new Element("br"));
        form.addChild(labelEndLoc);
        form.addChild(inputEndLoc);
        form.addChild(new Element("br"));
        form.addChild(submit);
        form.addChild(new Element("br"));
        body.addChild(form);

        return html;


    }
}

