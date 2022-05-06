package pt.isel.ls.view.html;

import pt.isel.ls.model.Routes;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;

public class RouteByIdView {

    private final Routes route;

    public RouteByIdView(Routes route) {
        this.route = route;
    }

    public Element htmlBuild() {

        Element html = new Element("html");
        Element head = new Element("head");
        html.addChild(head);
        Element body = new Element("body");
        Element list = new Element("ul");
        new NavigationBarView("Routes", "/routes", body);

        // Add object fields to bullet point list
        new HtmlText("Identifier : " + route.getId()).addList(list);
        new HtmlText("Start Location : " + route.getStartLoc()).addList(list);
        new HtmlText("End Location : " + route.getEndLoc()).addList(list);
        new HtmlText("Duration : " + route.getDuration()).addList(list);

        // Add title
        Element titleH1 = new Element("h1");
        titleH1.addChild(new HtmlText("Routes"));

        Element title = new Element("title");
        title.addChild(new HtmlText("Routes Details"));
        head.addChild(title);
        html.addChild(body);
        body.addChild(titleH1);
        body.addChild(list);

        return html;
    }
}
