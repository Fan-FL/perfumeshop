package script.User;

import datasource.CartMapper;
import datasource.UserMapper;
import domain.Cart;
import domain.Product;
import domain.User;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
//		response.setContentType("text/html");
//		System.out.println("Hello from Get" + request.getParameter("userName") + request.getParameter("passWord"));
//		PrintWriter writer = response.getWriter();
//		writer.println("<h3> Hello in html </h3>");
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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
            Map<Cart, Product> cartProductMap = CartMapper.GetAllCartInfoByUserID(userId);
            request.setAttribute("cartProductMap", cartProductMap);
        }
        getServletContext().getRequestDispatcher("/header.jsp").include(request, response);
    }

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
