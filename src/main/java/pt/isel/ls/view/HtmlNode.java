package pt.isel.ls.view;

import java.io.PrintWriter;

public interface HtmlNode {

    /**
     * Interface to run Html Classes.
     *
     * @param writer -  writer to output results
     */
    void toHtml(PrintWriter writer);

}
