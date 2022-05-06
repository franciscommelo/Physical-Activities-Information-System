package pt.isel.ls.dao;

import pt.isel.ls.model.Sport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


/**
 * Class to execute all querys from sports table
 */
public class SportsDao {

    private final Connection conn;
    private int affectedRows;
    LinkedList<Sport> sports = new LinkedList<>();
    ResultSet res = null;
    private Sport sport = null;

    public SportsDao(Connection conn) {
        this.conn = conn;
    }


    /**
     * Get all sports from table
     *
     * @param skip String
     * @param top  String
     * @return LinkedList sports
     * @throws Exception if nt exist
     */
    public LinkedList<Sport> getSports(String skip, String top) throws SQLException {

        PreparedStatement pstmt;
        String offset = skip != null ? " offset ? " : "";
        String limit = top != null ? " limit ? " : "";

        pstmt = (conn.prepareStatement("SELECT * FROM sport" + offset + limit));
        int count = 1;
        if (skip != null) {
            pstmt.setInt(count++, Integer.parseInt(skip));
        }
        if (top != null) {
            pstmt.setInt(count, Integer.parseInt(top));
        }

        res = pstmt.executeQuery();

        //List of sports
        sports = new LinkedList<>();

        while (res.next()) {
            sports.add(new Sport(res.getInt("sid"), res.getString("name"),
                    res.getString("description")));
        }


        return sports;
    }


    /**
     * Get sports by Id
     *
     * @param sid String
     * @return Sport sport
     * @throws Exception sports
     */
    public Sport getSportsById(String sid) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM sport WHERE sid = ?");
        pstmt.setInt(1, Integer.parseInt(sid));
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            sport = new Sport(res.getInt("sid"), res.getString("name"), res.getString("description"));
        }
        return sport;
    }


    /**
     * Insert a new sport to table
     *
     * @param name        String
     * @param description String
     * @return int affectedRows
     * @throws Exception if already exist
     */
    public int postSports(String name, String description) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SPORT (name, description) VALUES (?, ?);");
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        affectedRows = pstmt.executeUpdate();
        return affectedRows;
    }


    /**
     * Get sports by Route
     *
     * @return Sport sport
     * @throws Exception sports
     */
    public LinkedList<Sport> getSportsByRoute(String rid) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT "
                + "sport.sid, sport.name, sport.description "
                + "FROM activity Inner join route on route.rid = activity.rid inner JOIN sport on"
                + " sport.sid= activity.sid where route.rid = ?");
        pstmt.setInt(1, Integer.parseInt(rid));
        ResultSet res = pstmt.executeQuery();
        //List of sports
        sports = new LinkedList<>();
        while (res.next()) {
            sports.add(new Sport(res.getInt("sid"), res.getString("name"),
                    res.getString("description")));
        }
        return sports;
    }

    public int countSports() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT count (1) FROM SPORT");
        ResultSet res = pstmt.executeQuery();
        res.next();
        return res.getInt(1);
    }
}
