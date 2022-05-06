package pt.isel.ls.view;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Element implements HtmlNode {

    private final String name;


    private String attribute;

    private final LinkedList<HtmlNode> childs = new LinkedList<>();


    private final HashMap<String, String> attributes = new HashMap<>();

    public Element(String name) {
        this.name = name;
    }

    public Element(String name, String attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    /**
     * add child to childs
     *
     * @param child HtmlNode child
     */
    public void addChild(HtmlNode child) {
        childs.add(child);
    }

    /**
     * @return String
     */
    public String getName() {
        return name;
    }


    public void setAttribute(String attribute, String value) {
        attributes.put(attribute, value);
    }


    /**
     * print all class content
     *
     * @param writer -  writer to output results
     */
    public void toHtml(PrintWriter writer) {
        writer.write("<" + name + " ");

        Iterator it = attributes.entrySet().iterator();

        while (it.hasNext()) {
            HashMap.Entry att = (HashMap.Entry) it.next();

            writer.write(att.getKey().toString()
                    + "=\""
                    + att.getValue().toString()
                    + "\" ");
            it.remove(); // avoids a ConcurrentModificationException
        }

        writer.write(">");

        for (HtmlNode e : childs) {
            e.toHtml(writer);
        }
        writer.write("</" + name + ">");
    }
}
