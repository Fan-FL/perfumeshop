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

    public void userLogin(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = UserMapper.findByName(username);
            boolean logStatus = false;
            if(user != null){
                logStatus = password.equals(user.getPassword());
            }
            if(logStatus){
                request.getSession().setAttribute("userId", user.getId());
                request.getSession().setAttribute("userName", user.getUsername());
            }
            String jsonStr = "[{'logStatus':'" + logStatus + "'}]";
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }

    public void userLogout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public void userRegister(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String jsonStr = null;
            if ( UserMapper.findByName(username) != null) {
                String regStatus = "hasThisUser";
                jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
                JSONArray json = JSONArray.fromObject(jsonStr);
                out.write(json.toString());
                return;
            }
            if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                int userId = new UserMapper().insert(user);
                if (userId > 0) {
                    String regStatus = "regSuccess";
                    jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
                    JSONArray json = JSONArray.fromObject(jsonStr);
                    out.write(json.toString());
                    request.getSession().setAttribute("userId", userId);
                    request.getSession().setAttribute("userName", user.getUsername());
                    return;
                }
            }
            String regStatus = "regFail";
            jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }

    public void viewUser(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
        User user = UserMapper.findByID(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("accountMsg.jsp").forward(request, response);
    }
}
