package script.User;

import controller.FrontCommand;
import domain.User;
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
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
        User user = userService.findUserById(userId);
        request.setAttribute("user", user);
        forward("/accountMsg.jsp");
    }
}
