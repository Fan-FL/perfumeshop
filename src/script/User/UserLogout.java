package script.User;

import datasource.UserMapper;
import domain.User;

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

    public UserLogout() {
        super();
        // TODO Auto-generated constructor stub
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
        int userId = -1;
        try {
            userId = (Integer) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            System.out.println("user hasn't logged in!");
        }
        if(userId != -1){
            User userHeader = UserMapper.findByID(userId);
            System.out.println("login：[[ID:" + userId + ";User name:" + userHeader.getUsername() + "]]......");
            request.setAttribute("userHeader", userHeader);
        }
        getServletContext().getRequestDispatcher("/header.jsp").include(request, response);
    }
}
