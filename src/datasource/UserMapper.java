package datasource;

import domain.DomainObject;
import domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements IMapper{

    public static User findByName(String name) {
        String sql = "select USER_ID, USERNAME, PASSWORD, USER_STATUS, TRUENAME, PHONE, ADDRESS " +
                " from perfume.user " +
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
        if (user == null){
            return null;
        }else{
            User userInMap = IdentityMap.userMap.get(user.getId());
            if ( userInMap == null){
                IdentityMap.userMap.put(user.getId(), user);
            }else {
                return userInMap;
            }
            return user;
        }
    }

    public static User findByID(int id) {
        User user = IdentityMap.userMap.get(id);
        if (user != null){
            return user;
        }

        String sql = "select USER_ID, USERNAME, PASSWORD, USER_STATUS, TRUENAME, PHONE, ADDRESS " +
                " from perfume.user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
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
                IdentityMap.userMap.put(id, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return user;
    }

    @Override
    public int insert(DomainObject obj) {
        User user = (User)obj;
        String sql = "insert into perfume.user (username,password) values (?,?);";
        int userId = DBHelper.updateGetGeneratedKeys(sql, user.getUsername(), user.getPassword());
        user.setId(userId);
        IdentityMap.userMap.put(userId, user);
        return userId;
    }

    @Override
    public void update(DomainObject obj) {
        User user = (User)obj;
        String sql = "UPDATE perfume.user SET password=?,truename=?,phone=?,address=? WHERE USER_ID=?";
        DBHelper.update(sql, user.getPassword(), user.getTruename(),
                user.getPhone(), user.getAddress(), user.getId());
    }

    @Override
    public void delete(DomainObject obj) {

    }

    public static String getUserName(int userId) {
        String sql = "select  USERNAME" +
                " from perfume.user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        String data = "";
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                data = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return data;
    }

    public static String getTrueName(int userId) {
        String sql = "select  TRUENAME" +
                " from perfume.user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        String data = "";
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                data = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return data;
    }


    public static String getPhone(int userId) {
        String sql = "select  PHONE" +
                " from perfume.user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        String data = "";
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                data = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return data;
    }

    public static String getAddress(int userId) {
        String sql = "select  ADDRESS" +
                " from perfume.user " +
                " WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        String data = "";
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                data = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return data;
    }
}
