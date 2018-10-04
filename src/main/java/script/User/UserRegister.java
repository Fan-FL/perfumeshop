package script.User;

import controller.FrontCommand;
import domain.User;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import security.AppSession;
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
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
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
                            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                            token.setRememberMe(true);
                            //With most of Shiro, you'll always want to make sure you're working with the currently
                            //executing user, referred to as the subject -> will be hidden by AppSession class
                            Subject currentUser = SecurityUtils.getSubject();
                            AppSession.setAccountType(AppSession.USER_ROLE);
                            //Authenticate the subject by passing the user name and password token into the login method
                            currentUser.login(token);
                            // TODO set the user object
                            AppSession.init(user);
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
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
