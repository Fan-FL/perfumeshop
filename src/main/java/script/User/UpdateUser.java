package script.User;

import controller.FrontCommand;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class UpdateUser extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public UpdateUser() {
        super();
        userService = UserService.getInstance();
    }


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
        try {
            String password = request.getParameter("password");
            String truename = request.getParameter("truename");
            System.out.println(truename);
            System.out.println(password);
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            userService.updateUser(userId, password, truename, phone, address);
            forward("/FrontServlet?module=User&command=ViewUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
