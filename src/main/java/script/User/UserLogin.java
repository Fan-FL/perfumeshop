package script.User;

import service.CartService;
import service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    UserService userService = null;

    public UserLogin() {
        super();
        userService = UserService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String method = request.getParameter("method");
	    switch (method){
            case "header":
                this.responseHeaderInfo(request, response);
                break;
            case "login":
                this.login(request, response);
                break;
            default:
                break;
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

    /**
     * prepare header info
     * 1.check whether user has logged in from session
     * 2.load username from session and show it
     * 3.show product count in cart
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
            int cartCount = CartService.getInstance().getCartCount(userId);
            request.setAttribute("cartCount", cartCount);
        }
        getServletContext().getRequestDispatcher("/header.jsp").include(request, response);
    }


    /**
     * receive username and password
     * login, save userId and userName in session
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        userService.userLogin(request, response);
    }
}
