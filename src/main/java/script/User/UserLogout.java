package script.User;

import controller.FrontCommand;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class UserLogout extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public UserLogout() {
        super();
        userService = UserService.getInstance();
    }

    /**
     * log out
     * clear session
     */
    @Override
    public void process() throws ServletException, IOException {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            request.getSession().invalidate();
        }
        redirect("blank.jsp");
    }
}
