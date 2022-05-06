package pt.isel.ls.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import pt.isel.ls.router.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class AppServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(HttpServerApplication.class);

    private final Router router;

    public AppServlet(Router router) {
        this.router = router;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request: method={}, uri={}, accept={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getHeader("Accept"));

        resp.setStatus(303);
        processRequest(req,resp);
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {

        Path path = new Path(req.getRequestURI());

        log.info("new request received. path: {}" + path.getPath() + " and method " + req.getMethod());

        String acceptHeader = req.getHeader("Accept");

        if (acceptHeader.equals("*/*")) {
            acceptHeader = "text/html"; // default output format
        }
        acceptHeader = "accept:" + acceptHeader;

        Header header = new Header(acceptHeader);
        Method method = Method.valueOf(req.getMethod());
        CommandRequest cr = new CommandRequest(method, path, header, new Parameters(req.getParameterMap()));

        try {
            Optional<RouteResult> rs = router.findRoute(cr);
            if (rs.isPresent()) {

                CommandHandler ch = rs.get().cmdHandler;
                CommandResult response = ch.execute(cr);

                response.setContentType(acceptHeader.split(":")[1]);

                StringWriter writer = new StringWriter();
                PrintWriter pw = new PrintWriter(writer);
                Charset utf8 = StandardCharsets.UTF_8;
                resp.setContentType(resp.getHeader("Accept"));
                resp.setCharacterEncoding(utf8.name());
                response.getResultString(pw);
                pw.close();

                if (writer.toString().isEmpty()) {
                    resp.setStatus(404);
                } else {
                    byte[] respBodyBytes = writer.toString().getBytes();
                    resp.setContentLength(respBodyBytes.length);
                    resp.getOutputStream().write(respBodyBytes);
                }
            }
        } catch (Exception e) {
            resp.setStatus(400);
            e.printStackTrace();
        }
    }
}
