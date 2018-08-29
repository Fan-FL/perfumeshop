package script.User;

import datasource.UserMapper;
import domain.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/userregister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserRegister() {
        super();
        // TODO Auto-generated constructor stub
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
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String jsonStr = null;
            if ( UserMapper.findByName(username) != null) {
                String regStatus = "hasThisUser";
                jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
                JSONArray json = JSONArray.fromObject(jsonStr);
                out.write(json.toString());
                return;
            }
            if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){
                User user = new User(username, password);
                int userId = UserMapper.createUser(user);
                if (userId > 0) {
                    String regStatus = "regSuccess";
                    jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
                    JSONArray json = JSONArray.fromObject(jsonStr);
                    out.write(json.toString());
                    request.getSession().setAttribute("userId", userId);
                    request.getSession().setAttribute("userName", user.getUsername());
                    return;
                }
            }
            String regStatus = "regFail";
            jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
