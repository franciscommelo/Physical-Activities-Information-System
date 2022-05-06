package pt.isel.ls.view.html;

import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;

public class HomeView {

    public Element htmlBuild() {
        Element html = new Element("html");
        Element head = new Element("head");
        Element title = new Element("title");
        HtmlText titleText = new HtmlText("Root");

        title.addChild(titleText);
        html.addChild(head);
        head.addChild(title);

        Element users = new Element("li");
        users.addChild(new HtmlText("Users","/users?top=5&skip=0").linkFormat());
        Element sports = new Element("li");
        sports.addChild(new HtmlText("Sports","/sports?top=5&skip=0").linkFormat());
        Element routes = new Element("li");
        routes.addChild(new HtmlText("Routes","/routes?top=5&skip=0").linkFormat());
        Element body = new Element("body");
        html.addChild(body);
        HtmlText title1 = new HtmlText("HOMEPAGE");
        body.addChild(title1);
        Element list = new Element("u");
        body.addChild(list);
        list.addChild(users);
        list.addChild(sports);
        list.addChild(routes);

        return html;
    }

}
