package script.User;

import controller.FrontCommand;
import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import security.AppSession;
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
        boolean logStatus = false;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            //With most of Shiro, you'll always want to make sure you're working with the currently
            //executing user, referred to as the subject -> will be hidden by AppSession class
            Subject currentUser = SecurityUtils.getSubject();
            AppSession.setAccountType(AppSession.USER_ROLE);
            //Authenticate the subject by passing the user name and password token into the login method
            currentUser.login(token);
            // TODO set the user object
            User user = userService.findUserByName(username);
            AppSession.init(user);
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("userName", user.getUsername());
            logStatus = true;
        } catch (Exception e) {
            //username wasn't in the system or incorrect password
            e.printStackTrace();
            logStatus = false;
        } finally {
            String jsonStr = "[{'logStatus':'" + logStatus + "'}]";
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }
}
