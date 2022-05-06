package pt.isel.ls.visual;

import org.junit.Assert;
import org.junit.Test;
import pt.isel.ls.view.HtmlView;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class HtmlTest {

    @Test
    public void htmltest() throws IOException {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        StringBuilder str = new StringBuilder();
        str.append("id=1;name=Francisco;email=francisco1@gmail.com");
        new HtmlView().htmlBuild(str).toHtml(writer);
        String s = "<html ><head ><style>\n"
                + "table, th, td {\n"
                + "  border: 1px solid black;\n"
                + "  border-collapse: collapse;\n"
                + "}\n"
                + "</style><title ></title></head>"
                + "<body >id=1;name=Francisco;"
                + "email=francisco1@gmail.com</body></html>";
        Assert.assertEquals(s, stringWriter.toString());
        writer.close();
        stringWriter.close();
    }
}
