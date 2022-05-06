package pt.isel.ls.dao;

import pt.isel.ls.model.Sport;
import pt.isel.ls.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Class to execute all querys from Users table
 */
public class UsersDao {

    private int affectedRows;
    private Connection conn;


    public UsersDao(Connection conn) {
        this.conn = conn;
    }

    ResultSet res = null;
    LinkedList<User> users = null;
    LinkedList<Sport> sports = null;
    private User user = null;

    /**
     * Get all users
     *
     * @param skip String
     * @param top  String
     * @return LinkedList users
     * @throws Exception if not exists
     */

    public LinkedList<User> getUsers(String skip, String top) throws Exception {

        PreparedStatement pstmt;
        String offset = skip != null ? " offset ? " : "";
        String limit = top != null ? " limit ? " : "";

        pstmt = (conn.prepareStatement("SELECT * FROM users" + offset + limit));
        int count = 1;
        if (skip != null) {
            pstmt.setInt(count++, Integer.parseInt(skip));
        }
        if (top != null) {
            pstmt.setInt(count, Integer.parseInt(top));
        }

        res = pstmt.executeQuery();
        //List of users
        users = new LinkedList<>();
        while (res.next()) {
            users.add(new User(res.getInt("uid"), res.getString("name"),
                    res.getString("email")));
        }
        return users;
    }


    /**
     * Get users by id
     *
     * @param uid String
     * @return LinkedList users
     * @throws Exception if not exist
     */
    public User getUsersById(String uid) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM USERS where uid = ?");
        pstmt.setInt(1, Integer.parseInt(uid));
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            user = new User(res.getInt("uid"), res.getString("name"), res.getString("email"));
        }
        return user;
    }

    /**
     * Insert new user to table
     *
     * @param name  String
     * @param email String
     * @return int affectedRows
     * @throws Exception if already exist
     */
    public int postUsers(String name, String email) throws Exception {
        affectedRows = 0;
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USERS (name, email) VALUES (?, ?);");
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        affectedRows = pstmt.executeUpdate();
        return affectedRows;
    }


    public LinkedList<Sport> getSportsByUser(String uid) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT sport.sid, sport.name, sport.description "
                + "FROM activity inner join SPORT on sport.sid = activity.sid where uid = ?");
        pstmt.setInt(1, Integer.parseInt(uid));
        ResultSet res = pstmt.executeQuery();
        sports = new LinkedList<>();
        while (res.next()) {
            sports.add(new Sport(res.getInt("sid"), res.getString("name"),
                    res.getString("description")));
        }
        return sports;
    }

    public int countUsers() throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT count (1) FROM USERS");
        ResultSet res = pstmt.executeQuery();
        res.next();
        return res.getInt(1);
    }
}
