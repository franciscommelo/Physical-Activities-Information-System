package pt.isel.ls.view;

public class TableFormat {

    public void addHeads(Element e, String text) {

        Element temp = new Element("th");
        temp.addChild(new HtmlText(text));
        e.addChild(temp);
    }

    public void addRow(Element e, String text) {
        Element temp = new Element("td");
        temp.addChild(new HtmlText(text));
        e.addChild(temp);
    }

    public String linkFormat(String uri, String text) {
        return "<a href=\"" + uri + "\">" + text + "</a>";
    }
}
