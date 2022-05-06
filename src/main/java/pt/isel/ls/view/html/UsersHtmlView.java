package pt.isel.ls.view.html;

import pt.isel.ls.model.User;
import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;
import pt.isel.ls.view.TableFormat;
import java.util.LinkedList;

public class UsersHtmlView {

    LinkedList<User> users = new LinkedList<>();

    private int skip;
    private int count;

    public UsersHtmlView(Object user, int count, int skip) {

        this.skip = skip;
        this.count = count;
        if (!(user instanceof LinkedList)) {
            users.add((User) user);
        } else {
            users = (LinkedList<User>) user;
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
        titleH1.addChild(new HtmlText("USERS"));
        Element title = new Element("title");
        title.addChild(new HtmlText("Users Page"));
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
        tab.addHeads(headers, "email");

        for (User u : users) {
            Element row = new Element("tr");
            tab.addRow(row, tab.linkFormat("/users/" + u.getId(), String.valueOf(u.getId())));
            tab.addRow(row, u.getName());
            tab.addRow(row, u.getEmail());
            table.addChild(row);
        }
        if (withPaging) {
            Element paging = new Element("p");
            body.addChild(paging);
            new HtmlText().pagination(skip, count, "users", paging);
        }

        Element createUserH1 = new Element("h1");
        body.addChild(createUserH1);
        createUserH1.addChild(new HtmlText("Create new User"));


        Element labelName = new Element("label");
        labelName.addChild(new HtmlText("Name:"));
        Element inputName = new Element("input");
        inputName.setAttribute("id","name");
        inputName.setAttribute("name","name");


        Element labelEmail = new Element("label");
        labelEmail.addChild(new HtmlText("Email: "));
        Element inputEmail = new Element("input");
        inputEmail.setAttribute("id","email");
        inputEmail.setAttribute("name","email");


        Element submit = new Element("input");
        submit.setAttribute("type","submit");
        submit.setAttribute("value","Submit");

        Element form = new Element("form");
        form.setAttribute("method","POST");
        form.addChild(labelName);
        form.addChild(inputName);
        form.addChild(new Element("br"));
        form.addChild(labelEmail);
        form.addChild(inputEmail);
        form.addChild(new Element("br"));
        form.addChild(submit);
        form.addChild(new Element("br"));
        body.addChild(form);

        return html;
    }
}

