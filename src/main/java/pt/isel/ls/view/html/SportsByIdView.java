package pt.isel.ls.view.html;

import pt.isel.ls.model.Sport;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;
import pt.isel.ls.view.html.NavigationBarView;

public class SportsByIdView {

    private final Sport sport;

    public SportsByIdView(Sport sport) {
        this.sport = sport;
    }

    public Element htmlBuild() {

        Element html = new Element("html");
        Element head = new Element("head");
        html.addChild(head);

        Element body = new Element("body");
        Element list = new Element("ul");

        new NavigationBarView("Sports","/sports",body);

        // Add object fields to bullet point list
        new HtmlText("Identifier : " + sport.getId()).addList(list);
        new HtmlText("Name : " + sport.getName()).addList(list);
        new HtmlText("Description : " + sport.getDescription()).addList(list);
        new HtmlText("Activities : " + sport.getDescription()).addList(list);


        TableFormat tab = new TableFormat();
        new HtmlText(""
                + tab.linkFormat("/sports/" + sport.getId() + "/activities?top=5&skip=0",
                "Activity by sport")).addList(list);
        // Add title
        Element titleH1 = new Element("h1");
        titleH1.addChild(new HtmlText("Sports"));

        Element title = new Element("title");
        title.addChild(new HtmlText("Sports Details"));
        head.addChild(title);
        html.addChild(body);
        body.addChild(titleH1);
        body.addChild(list);

        return html;
    }
}
