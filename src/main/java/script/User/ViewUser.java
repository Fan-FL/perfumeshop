package script.User;

import controller.FrontCommand;
import domain.User;
import security.AppSession;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ViewUser extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public ViewUser() {
        super();
        userService = UserService.getInstance();
    }

    /**
     * prepare user info and show it in page
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                User user = userService.findUserById(AppSession.getId());
                request.setAttribute("user", user);
                forward("/accountMsg.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
