package pt.isel.ls.dao;

import pt.isel.ls.model.Activity;
import pt.isel.ls.model.Routes;
import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Class to execute all querys from Activity table
 */
public class ActivitiesDao {

    DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
    private final Connection conn;
    private int affectedRows;
    LinkedList<Activity> activities;

    public ActivitiesDao(Connection conn) {
        this.conn = conn;
    }


    /**
     * Execute query to insert new activities
     *
     * @param date     String
     * @param duration String
     * @param uid      String
     * @param sid      String
     * @param rid      String
     * @return int affectedRows
     * @throws Exception if exist
     */
    public int postActivities(String date, String duration, String uid, String sid, String rid) throws Exception {

        String query;

        if (rid != null) {
            query = "INSERT INTO ACTIVITY (date,duration,uid,sid,rid) "
                    + "VALUES (?,?,?,?,?)";
        } else {
            query = "INSERT INTO ACTIVITY (date,duration,uid,sid) "
                    + "VALUES (?,?,?,?)";
        }


        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setDate(1, Date.valueOf(date));
        try {
            pstmt.setTime(2, convertTime(duration));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pstmt.setInt(3, Integer.parseInt(uid));
        pstmt.setInt(4, Integer.parseInt(sid));
        if (rid != null) {
            pstmt.setInt(5, Integer.parseInt(rid));
        }
        affectedRows = pstmt.executeUpdate();

        return affectedRows;
    }

    /**
     * Execute query to get Activities by specific user id
     *
     * @param uid  String
     * @param skip String
     * @param top  String
     * @return LinkedList activities
     * @throws Exception if not found
     */
    public LinkedList<Activity> getActivitiesByUser(String uid, String skip, String top) throws Exception {

        activities = new LinkedList<>();

        String offset = skip != null ? " offset ? " : "";
        String limit = top != null ? " limit ? " : "";

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM activity "
                + "WHERE uid = ? AND deletedAt is null " + offset + limit);
        pstmt.setInt(1, Integer.parseInt(uid));
        int count = 2;
        if (skip != null) {
            pstmt.setInt(count++, Integer.parseInt(skip));
        }
        if (top != null) {
            pstmt.setInt(count, Integer.parseInt(top));
        }


        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            User user = new UsersDao(conn).getUsersById(res.getString("uid"));
            Sport sport = new SportsDao(conn).getSportsById(res.getString("sid"));
            Routes routes = new RoutesDao(conn).getRoutesById(res.getString("rid"));
            activities.add(new Activity(res.getInt("aid"), res.getString("date"),
                    res.getString("duration"),
                    user,
                    routes,
                    sport));
        }
        return activities;
    }

    /**
     * Execute query to get Activities by specific Sport id
     *
     * @param sid  String
     * @param skip String
     * @param top  String
     * @return LinkedList activities
     * @throws Exception if not found
     */
    public LinkedList<Activity> getActivitiesBySport(String sid, String skip, String top) throws Exception {

        activities = new LinkedList<>();

        String offset = skip != null ? " offset ? " : "";
        String limit = top != null ? " limit ? " : "";

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM activity "
                + "WHERE sid = ? AND deletedAt is null" + offset + limit);

        pstmt.setInt(1, Integer.parseInt(sid));
        int count = 2;
        if (skip != null) {
            pstmt.setInt(count++, Integer.parseInt(skip));
        }
        if (top != null) {
            pstmt.setInt(count, Integer.parseInt(top));
        }

        ResultSet res = pstmt.executeQuery();

        activities = new LinkedList<>();
        while (res.next()) {
            User user = new UsersDao(conn).getUsersById(res.getString("uid"));
            Sport sport = new SportsDao(conn).getSportsById(res.getString("sid"));
            Routes routes = new RoutesDao(conn).getRoutesById(res.getString("rid"));
            activities.add(new Activity(res.getInt("aid"), res.getString("date"),
                    res.getString("duration"),
                    user,
                    routes,
                    sport));
        }

        return activities;
    }

    /**
     * Execute query to get Activities by specific id
     *
     * @param aid String
     * @param sid String
     * @return LinkedList activities
     * @throws Exception if not found
     */
    public LinkedList<Activity> getActivitiesById(String aid, String sid) throws Exception {

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ACTIVITY "
                + "WHERE aid = ? AND sid = ? AND deletedAt is null ");
        pstmt.setInt(1, Integer.parseInt(aid));
        pstmt.setInt(2, Integer.parseInt(sid));
        ResultSet res = pstmt.executeQuery();


        activities = new LinkedList<>();

        if (res.next()) {
            User user = new UsersDao(conn).getUsersById(res.getString("uid"));
            Sport sport = new SportsDao(conn).getSportsById(res.getString("sid"));
            Routes routes = new RoutesDao(conn).getRoutesById(res.getString("rid"));
            activities.add(new Activity(res.getInt("aid"), res.getString("date"),
                    res.getString("duration"),
                    user,
                    routes,
                    sport));
        }
        return activities;
    }

    /**
     * Execute query to get Activities by specific order
     *
     * @param sid          String
     * @param distance     String
     * @param date         String
     * @param rid          String
     * @param orderbyParam String
     * @return LinkedList activities
     * @throws Exception if not found
     */
    public LinkedList<Activity> getActivitiesByTops(String sid, String distance,
                                                    String date, String rid, String orderbyParam) throws Exception {

        String orderby = null;
        String dateOpt = "";
        String ridOpt = "";
        String query;

        if (orderbyParam.equals("ascending")) {
            orderby = "ASC ";
        } else if (orderbyParam.equals("descending")) {
            orderby = "DESC ";
        }


        if (date != null) {
            dateOpt += " AND date = ? ";
        }

        if (rid != null) {
            ridOpt += "AND rid = ?";
        }


        if (distance == null) {
            query = "SELECT * FROM ACTIVITY WHERE sid = ? AND deletedAt is null  " + dateOpt
                    + ridOpt + " ORDER BY duration " + orderby;
        } else {
            query = "select aid,date, duration, uid,sid,activity.rid,distance "
                    + "from activity "
                    + "inner join route "
                    + "on activity.rid = route.rid "
                    + "WHERE sid = ? AND distance > ? AND deletedAt is null " + dateOpt
                    + ridOpt + " ORDER BY duration " + orderby;
        }


        PreparedStatement pstmt = conn.prepareStatement(query);

        int curr = 1;
        pstmt.setInt(curr++, Integer.parseInt(sid));
        if (distance != null) {
            pstmt.setInt(curr++, Integer.parseInt(distance));
        }
        if (date != null) {
            try {
                pstmt.setDate(curr++, Date.valueOf(date));
            } catch (Exception e) {
                throw new SQLException("Invalid date format");
            }
        }
        if (rid != null) {
            pstmt.setInt(curr++, Integer.parseInt(rid));
        }

        ResultSet res = pstmt.executeQuery();
        activities = new LinkedList<>();
        while (res.next()) {
            User user = new UsersDao(conn).getUsersById(res.getString("uid"));
            Sport sport = new SportsDao(conn).getSportsById(res.getString("sid"));
            Routes routes = new RoutesDao(conn).getRoutesById(res.getString("rid"));
            activities.add(new Activity(res.getInt("aid"), res.getString("date"),
                    res.getString("duration"),
                    user,
                    routes,
                    sport));
        }
        return activities;
    }

    /**
     * Convert String to time format
     *
     * @param time String
     * @return Time
     * @throws ParseException the position where the error is found while parsing
     */
    Time convertTime(String time) throws ParseException {
        return new java.sql.Time(formatter.parse(time).getTime());
    }

    public void deleteActivities(LinkedList<String> activities, int uid) throws Exception {

        for (String aid : activities) {

            //  Check if activity aid belongs to user uid
            PreparedStatement pstmt1 = conn.prepareStatement("SELECT * from Activity where uid = ? AND aid = ?");
            pstmt1.setInt(1, uid);
            pstmt1.setInt(2, Integer.parseInt(aid));
            ResultSet res1 = pstmt1.executeQuery();
            if (!res1.next()) {
                throw new Exception("Activity" + aid + " doesn't belong to the user " + uid);
            }

            PreparedStatement pstmt2 = conn.prepareStatement("SELECT deletedat from Activity where aid = ?");
            pstmt2.setInt(1, Integer.parseInt(aid));
            ResultSet res2 = pstmt1.executeQuery();
            res2.next();
            if (res2.getDate("deletedat") != null) {
                throw new Exception("The activity " + Integer.parseInt(aid) + " was already deleted!");
            }

            //  Update activity
            PreparedStatement pstmt3 = conn.prepareStatement("UPDATE activity SET deletedAt = NOW() where aid = ?");
            pstmt3.setInt(1, Integer.parseInt(aid));
            int affectedRows = pstmt3.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("No rows affected");
            }
        }
    }

    public int countActivities() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT count (1) FROM ACTIVITY");
        ResultSet res = pstmt.executeQuery();
        res.next();
        return res.getInt(1);
    }
}
