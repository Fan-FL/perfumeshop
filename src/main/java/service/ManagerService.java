package service;

import datasource.ManagerMapper;
import datasource.UserMapper;
import domain.Manager;
import domain.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerService {
    private static ManagerService instance;
    static {
        instance = new ManagerService();
    }
    public static ManagerService getInstance() {
        return instance;
    }

    private ManagerService(){}

    public void managerLogin(HttpServletRequest request, HttpServletResponse response){
        String managerName = request.getParameter("managerName");
        String managerPassword = request.getParameter("managerPassword");
        Manager manager = null;
        manager = ManagerMapper.findByName(managerName);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String jsonStr = null;
            if (manager == null || !managerPassword.equals(manager.getPassword())) {
                jsonStr = "[{'check':'checkout'}]";
            } else {
                jsonStr = "[{'check':'checkin'}]";
                request.getSession().setAttribute("manager", manager);
            }
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
