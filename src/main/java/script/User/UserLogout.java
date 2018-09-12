package script.User;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class UserLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    UserService userService = null;

    public UserLogout() {
        super();
        userService = UserService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("blank.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

    /**
     * log out
     * clear session
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void responseHeaderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        userService.userLogout(request, response);
        request.getRequestDispatcher("/header.jsp").include(request, response);
    }
}
