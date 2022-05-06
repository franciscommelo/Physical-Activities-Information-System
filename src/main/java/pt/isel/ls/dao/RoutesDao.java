package pt.isel.ls.dao;

import pt.isel.ls.model.Routes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Class to execute all querys from routes table
 */
public class RoutesDao {

    private final Connection conn;

    ResultSet res = null;
    LinkedList<Routes> routes;
    private int affectedRows;
    private Routes route = null;

    public RoutesDao(Connection conn) {
        this.conn = conn;
    }


    /**
     * Get Routes by id
     *
     * @param rid String
     * @return LinkedList routes
     * @throws Exception if not exist
     */
    public Routes getRoutesById(String rid) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ROUTE where rid = ?");
        pstmt.setInt(1, Integer.parseInt(rid));
        res = pstmt.executeQuery();

        if (res.next()) {
            route = new Routes(res.getInt("rid"), res.getString("startLocation"),
                    res.getString("endLocation"), res.getString("distance"));
        }
        return route;
    }


    /**
     * Get all routes
     *
     * @param skip String
     * @param top  String
     * @return LinkedList routes
     * @throws Exception if not exist
     */
    public LinkedList<Routes> getRoutes(String skip, String top) throws SQLException {

        String offset = skip != null ? " offset ? " : "";
        String limit = top != null ? " limit ? " : "";

        PreparedStatement pstmt = (conn.prepareStatement("SELECT * FROM ROUTE" + offset + limit));
        int count = 1;
        if (skip != null) {
            pstmt.setInt(count++, Integer.parseInt(skip));
        }
        if (top != null) {
            pstmt.setInt(count, Integer.parseInt(top));
        }

        ResultSet res = pstmt.executeQuery();

        routes = new LinkedList<>();

        while (res.next()) {
            routes.add(new Routes(res.getInt("rid"), res.getString("startLocation"),
                    res.getString("endLocation"), res.getString("distance")));
        }
        return routes;
    }


    /**
     * Insert a new route to table
     *
     * @param startLocation String
     * @param endLocation   String
     * @param distance      String
     * @return int affectedRows
     * @throws Exception if exist
     */
    public int postRoutes(String startLocation, String endLocation, String distance) throws Exception {
        affectedRows = 0;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Route (startLocation, endLocation, distance) "
                + "VALUES (?, ?, ?);");
        pstmt.setString(1, startLocation);
        pstmt.setString(2, endLocation);
        pstmt.setDouble(3, Double.parseDouble(distance));
        affectedRows = pstmt.executeUpdate();
        return affectedRows;
    }

    public int countRoutes() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT count (1) FROM Route");
        ResultSet res = pstmt.executeQuery();
        res.next();
        return res.getInt(1);
    }

}
