package pt.isel.ls.view;


public class HtmlView {

    /**
     * Create HTML structure
     *
     * @param strBuilder StringBuilder
     * @return Element html
     */
    public Element htmlBuild(StringBuilder strBuilder) {

        Element html = new Element("html");
        Element head = new Element("head");
        Element title = new Element("title");
        HtmlText tableStyle = new HtmlText("<style>\n"
                + "table, th, td {\n"
                + "  border: 1px solid black;\n"
                + "  border-collapse: collapse;\n"
                + "}\n"
                + "</style>");

        html.addChild(head);
        head.addChild(tableStyle);
        head.addChild(title);

        Element body = new Element("body");
        HtmlText tableHtmlText = new HtmlText(strBuilder.toString());
        HtmlText titleHtmlText = new HtmlText("");
        html.addChild(body);
        title.addChild(titleHtmlText);
        body.addChild(tableHtmlText);

        return html;
    }


}
