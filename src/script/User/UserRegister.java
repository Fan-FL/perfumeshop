package script.User;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userregister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    UserService userService = null;

    public UserRegister() {
        super();
        userService = UserService.getInstance();
    }

    /**
     * receive username and password from page and create user in DB
     * if failed, show prompt
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.userRegister(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
