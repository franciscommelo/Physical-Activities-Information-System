package pt.isel.ls.view.html;

import pt.isel.ls.view.Element;
import pt.isel.ls.view.HtmlText;

public class NavigationBarView {

    final String homeUri = "/";

    public NavigationBarView(String text, String rootUri, Element body) {
        body.addChild(new HtmlText("Home", homeUri).linkFormat());
        body.addChild(new HtmlText(text, rootUri + "?skip=0&top=5").linkFormat());
    }

    public NavigationBarView(Element body) {
        body.addChild(new HtmlText("Home", homeUri).linkFormat());
    }
}
