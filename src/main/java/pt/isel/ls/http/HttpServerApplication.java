package pt.isel.ls.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.router.Router;

public class HttpServerApplication {

    private static final Logger log = LoggerFactory.getLogger(HttpServerApplication.class);
    private final int port;
    private final Router router;
    private final HttpPool httpPool;

    public HttpServerApplication(int port, Router router, HttpPool httpPool) {
        this.router = router;
        this.port = port;
        this.httpPool = httpPool;
    }


    public void run() throws Exception {
        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        AppServlet appServlet = new AppServlet(router);

        handler.addServletWithMapping(new ServletHolder(appServlet), "/*");
        server.setHandler(handler);
        server.start();
        httpPool.addServer(port, server);

        try {
            server.start();
            log.info("server started listening on port {}", port);
            log.info("You can use command Close / port=" + port + " to close the connetion");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
