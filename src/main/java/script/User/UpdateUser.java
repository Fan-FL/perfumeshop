package script.User;

import controller.FrontCommand;
import domain.OrderMsg;
import security.AppSession;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class UpdateUser extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public UpdateUser() {
        super();
        userService = UserService.getInstance();
    }


    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                String password = request.getParameter("password");
                String truename = request.getParameter("truename");
                System.out.println(truename);
                System.out.println(password);
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                userService.updateUser(AppSession.getId(), password, truename, phone, address);
                forward("/FrontServlet?module=User&command=ViewUser");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
