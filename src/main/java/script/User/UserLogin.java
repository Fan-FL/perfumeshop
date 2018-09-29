package script.User;

import controller.FrontCommand;
import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;
import service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

public class UserLogin extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public UserLogin() {
        super();
        userService = UserService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            userService.findUserByName(username);
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
}
