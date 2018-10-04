package script.manager;

import controller.FrontCommand;
import domain.Manager;
import domain.User;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import security.AppSession;
import service.ManagerService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerLogin extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ManagerService managerService = null;

    public ManagerLogin() {
        super();
        managerService = ManagerService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        PrintWriter out = null;
        String jsonStr = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String managerName = request.getParameter("managerName");
            String managerPassword = request.getParameter("managerPassword");
            UsernamePasswordToken token = new UsernamePasswordToken(managerName, managerPassword);
            token.setRememberMe(true);
            //With most of Shiro, you'll always want to make sure you're working with the currently
            //executing user, referred to as the subject -> will be hidden by AppSession class
            Subject currentUser = SecurityUtils.getSubject();
            AppSession.setAccountType(AppSession.MANAGER_ROLE);
            //Authenticate the subject by passing the user name and password token into the login method
            currentUser.login(token);
            // TODO set the user object
            Manager manager = managerService.findByName(managerName);
            AppSession.init(manager);
            request.getSession().setAttribute("manager", manager);
            jsonStr = "[{'check':'checkin'}]";
        } catch (Exception e) {
            //username wasn't in the system or incorrect password
            e.printStackTrace();
            jsonStr = "[{'check':'checkout'}]";
        } finally {
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
            out.flush();
            out.close();
        }
    }
}
