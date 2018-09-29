package datasource;

import domain.DomainObject;
import domain.Manager;
import domain.Money;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerMapper implements IMapper{

    public static Manager findByID(int id) {
        Manager manager = null;
        String sql = "select MANAGER_ID, USERNAME, PASSWORD, TITLE, AMOUNT, CURRENCY" +
                " from perfume.Manager " +
                " WHERE MANAGER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                String title = rs.getString(4);
                float amount = rs.getFloat(5);
                String currency = rs.getString(6);

                Money salary = new Money(amount, currency);

                manager = new Manager(id, username, pwd, title, salary);
                manager.setType("Manager");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return manager;
    }

    public static Manager findByName(String name) {
        Manager manager = null;
        String sql = "select MANAGER_ID, USERNAME, PASSWORD, TITLE, AMOUNT, CURRENCY" +
                " from perfume.Manager " +
                " WHERE USERNAME = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                String title = rs.getString(4);
                float amount = rs.getFloat(5);
                String currency = rs.getString(6);

                Money salary = new Money(amount, currency);

                manager = new Manager(id, username, pwd, title, salary);
                manager.setType("Manager");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return manager;
    }

    @Override
    public int insert(DomainObject obj) {
        Manager manager = (Manager) obj;
        String sql = "insert into perfume.user (username,password) values (?,?);";
        int userId = DBHelper.updateGetGeneratedKeys(sql, manager.getUsername(), manager.getPassword());
        manager.setId(userId);
        return userId;
    }

    @Override
    public void update(DomainObject obj) {
        Manager manager = (Manager)obj;
        String sql = "UPDATE perfume.Manager SET USERNAME =?, PASSWORD =?, TITLE = ?, " +
                "AMOUNT = ?, CURRENCY = ?" +
                " WHERE MANAGER_ID = ?";
        DBHelper.update(sql, manager.getUsername(), manager.getPassword(),
                manager.getTitle(), manager.getSalary().getAmount(), manager.getSalary().getCurrency());
    }

    @Override
    public void delete(DomainObject obj) {
        Manager manager = (Manager)obj;
        String sql = "Delete FROM perfume.Manager WHERE MANAGER_ID = ?";
        DBHelper.update(sql, manager.getId());
    }
}
