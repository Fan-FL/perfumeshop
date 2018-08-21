package script.User;

import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/logout")
public class UserLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("blank.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

    public void responseHeaderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = -1;
        try {
            userId = (Integer) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            System.out.println("用户未登录，身份：游客！！");
        }
        if(userId != -1){
            User userHeader = UserMapper.findByID(userId);
            System.out.println("用户登录：[[ID:" + userId + ";用户名：" + userHeader.getUsername() + "]]......");
            request.setAttribute("userHeader", userHeader);
//            Map<Cart,Product> cartProductMap = getCartInfo(request, response);
//            request.setAttribute("cartProductMap", cartProductMap);
        }
        getServletContext().getRequestDispatcher("/header.jsp").include(request, response);
    }
}
