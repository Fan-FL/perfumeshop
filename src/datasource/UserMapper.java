package datasource;

import domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User findByName(String name) {
        String sql = "select USER_ID, USERNAME, PASSWORD, USER_STATUS, TRUENAME, PHONE, ADDRESS " +
                " from user " +
                " WHERE USERNAME = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        User user = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt(1);
                String pwd = rs.getString(3);
                int status = rs.getInt(4);
                String trueName = rs.getString(5);
                String phone = rs.getString(6);
                String address = rs.getString(7);
                user = new User(id, name, pwd, trueName, phone, address, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return user;
    }

    public static User findByID(int id) {
        String sql = "select USER_ID, USERNAME, PASSWORD, USER_STATUS, TRUENAME, PHONE, ADDRESS " +
                " from user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        User user = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                int status = rs.getInt(4);
                String trueName = rs.getString(5);
                String phone = rs.getString(6);
                String address = rs.getString(7);
                user =  new User(id, username, pwd, trueName, phone, address, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return user;
    }

    public static void updateUser(int userId, String password, String truename,
                               String phone, String address) {
        User user = UserMapper.findByID(userId);
        User userupdate = new User(userId, user.getUsername(), password, truename, phone, address);
        String sql = "UPDATE user SET password=?,truename=?,phone=?,address=? WHERE USER_ID=?";
        DBHelper.update(sql, userupdate.getPassword(), userupdate.getTruename(),
                userupdate.getPhone(), userupdate.getAddress(), userupdate.getUserId());
    }

    public static int createUser(User user) {
        String sql = "insert into user (username,password) values (?,?);";
        return DBHelper.updateGetGeneratedKeys(sql, user.getUsername(), user.getPassword());
    }
}
