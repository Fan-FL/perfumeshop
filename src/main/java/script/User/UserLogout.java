package script.User;

import controller.FrontCommand;
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
        userService.userLogout(request.getSession());
        redirect("blank.jsp");
    }
}
