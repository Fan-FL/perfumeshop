package script.User;

import datasource.CartMapper;
import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserLogin() {
        super();
        // TODO Auto-generated constructor stub
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
            int cartCount = CartMapper.getCartCount(userId);
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
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = UserMapper.findByName(username);
            boolean logStatus = false;
            if(user != null){
                logStatus = password.equals(user.getPassword());
            }
            if(logStatus){
                request.getSession().setAttribute("userId", user.getUserId());
                request.getSession().setAttribute("userName", user.getUsername());
            }
            String jsonStr = "[{'logStatus':'" + logStatus + "'}]";
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }
}
