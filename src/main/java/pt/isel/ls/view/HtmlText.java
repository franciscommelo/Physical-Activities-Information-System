package pt.isel.ls.view;

import java.io.PrintWriter;

public class HtmlText implements HtmlNode {

    private final String text;
    private final String uri;


    public HtmlText(String text) {
        this.text = text;
        uri = null;
    }

    public HtmlText(String text, String uri) {
        this.text = text;
        this.uri = uri;
    }

    public HtmlText() {
        uri = null;
        text = null;
    }


    /**
     * print results to html file with selected format
     *
     * @param writer -  writer to output results
     */
    @Override
    public void toHtml(PrintWriter writer) {
        writer.write(text);
    }


    public void addList(Element e) {
        Element temp = new Element("li");
        temp.addChild(new HtmlText(text));
        e.addChild(temp);
    }


    public HtmlText linkFormat() {
        return new HtmlText("<a href=\"" + uri + "\">" + text + "</a>\n");
    }


    public void pagination(int skip, int count, String pageName, Element paging) {

        Element prev = new Element("a");
        Element next = new Element("a");
        Element page = new Element("a");

        paging.addChild(prev);
        paging.addChild(page);
        paging.addChild(next);

        // Previous Link
        if (skip >= 5) {
            prev.addChild(new HtmlText("<<", "/" + pageName + "?skip=" + (skip - 5) + "&top=" + 5).linkFormat());
        } else {
            prev.addChild(new HtmlText("<<"));
        }

        page.addChild(new HtmlText("| " + ((skip / 5) + 1) + " | "));

        // Next Link
        if (count - 5 > skip) {
            next.addChild(new HtmlText(">>", "/" + pageName + "?skip=" + (skip + 5) + "&top=" + 5).linkFormat());
        } else {
            next.addChild(new HtmlText(">>"));
        }
    }
}
