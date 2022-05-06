package pt.isel.ls.view.html;

import pt.isel.ls.model.User;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;

public class UsersByIdView {

    private final User user;

    public UsersByIdView(User user) {
        this.user = user;
    }

    public Element htmlBuild() {

        Element html = new Element("html");
        Element head = new Element("head");
        html.addChild(head);
        Element body = new Element("body");
        Element list = new Element("ul");

        new NavigationBarView("Users", "/users", body);

        // Add object fields to bullet point list
        new HtmlText("Identifier : " + user.getId()).addList(list);
        new HtmlText("Name : " + user.getName()).addList(list);
        new HtmlText("E-mail : " + user.getEmail()).addList(list);

        // Add title
        Element titleH1 = new Element("h1");
        titleH1.addChild(new HtmlText("User"));

        Element title = new Element("title");
        title.addChild(new HtmlText("User Details"));
        head.addChild(title);
        html.addChild(body);
        body.addChild(titleH1);
        body.addChild(list);

        return html;
    }
}
