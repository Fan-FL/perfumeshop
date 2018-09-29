package service;

import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserService {
    private static UserService instance;
    static {
        instance = new UserService();
    }
    public static UserService getInstance() {
        return instance;
    }

    private UserService(){}

    public void updateUser(int userId, String password, String truename,
                                     String phone, String address){
        User user = UserMapper.findByID(userId);
        user.setPassword(password);
        user.setTruename(truename);
        user.setPhone(phone);
        user.setAddress(address);
        new UserMapper().update(user);
    }

    public User findUserByName(String username){
        return UserMapper.findByName(username);
    }

    public void userLogout(HttpSession session){
        session.invalidate();
    }

    public int insert(User user) {
        return new UserMapper().insert(user);
    }

    public User findUserById(int userId) {
        return UserMapper.findByID(userId);
    }
}
