package script.User;

import controller.FrontCommand;
import domain.User;
import net.sf.json.JSONArray;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRegister extends FrontCommand {
	private static final long serialVersionUID = 1L;

    UserService userService = null;

    public UserRegister() {
        super();
        userService = UserService.getInstance();
    }


    /**
     * receive username and password from page and create user in DB
     * if failed, show prompt
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void process() throws ServletException, IOException {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String jsonStr = null;
            if ( userService.findUserByName(username) != null) {
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
                int userId = userService.insert(user);
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
}
