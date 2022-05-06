package pt.isel.ls.view.html;

import pt.isel.ls.model.Sport;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;
import java.util.LinkedList;

public class SportsHtmlView {

    LinkedList<Sport> sports = new LinkedList<>();

    private int skip;
    private int count;

    public SportsHtmlView(Object sport, int count, int skip) {
        this.skip = skip;
        this.count = count;

        if (!(sport instanceof LinkedList)) {
            sports.add((Sport) sport);
        } else {
            sports = (LinkedList<Sport>) sport;
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

        Element titleH1 = new Element("h1");
        HtmlText tableStyle = new HtmlText("<style>\n"
                + "table, th, td {\n"
                + "  border: 1px solid black;\n"
                + "  border-collapse: collapse;\n"
                + "}\n"
                + "</style>");
        head.addChild(tableStyle);
        titleH1.addChild(new HtmlText("Sport"));
        Element title = new Element("title");
        title.addChild(new HtmlText("Sport Page"));
        head.addChild(title);
        html.addChild(body);
        body.addChild(titleH1);
        Element table = new Element("table");
        table.setAttribute("style","width:25%");

        Element headers = new Element("tr");
        table.addChild(headers);


        body.addChild(table);

        TableFormat tab = new TableFormat();
        tab.addHeads(headers, "id");
        tab.addHeads(headers, "name");
        tab.addHeads(headers, "description");


        for (Sport sport : sports) {


            Element row = new Element("tr");
            tab.addRow(row, tab.linkFormat("/sports/" + sport.getId(), String.valueOf(sport.getId())));
            tab.addRow(row, sport.getName());
            tab.addRow(row, sport.getDescription());
            table.addChild(row);
        }
        if (withPaging) {
            Element paging = new Element("p");
            body.addChild(paging);

            new HtmlText().pagination(skip, count, "sports", paging);
        }

        Element createUserH1 = new Element("h1");
        body.addChild(createUserH1);
        createUserH1.addChild(new HtmlText("Create new Sport"));

        Element labelName = new Element("label");
        labelName.addChild(new HtmlText("Name:"));
        Element inputName = new Element("input");

        inputName.setAttribute("id","name");
        inputName.setAttribute("name","name");


        Element labelDescription = new Element("label");
        labelDescription.addChild(new HtmlText("Description: "));
        Element inputDescription = new Element("input");
        inputDescription.setAttribute("id","description");
        inputDescription.setAttribute("name","description");


        Element submit = new Element("input");
        submit.setAttribute("type","submit");
        submit.setAttribute("value","Submit");

        Element form = new Element("form");
        form.addChild(labelName);
        form.addChild(inputName);
        form.addChild(new Element("br"));
        form.addChild(labelDescription);
        form.addChild(inputDescription);
        form.addChild(new Element("br"));
        form.addChild(submit);
        form.addChild(new Element("br"));
        body.addChild(form);

        return html;
    }





}

