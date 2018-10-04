package script.manager;

import controller.FrontCommand;
import domain.Manager;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import service.ManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerLogout extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ManagerService managerService = null;

    public ManagerLogout() {
        super();
        managerService = ManagerService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            request.getSession().invalidate();
        }
        redirect("/back/login.jsp");
    }
}
