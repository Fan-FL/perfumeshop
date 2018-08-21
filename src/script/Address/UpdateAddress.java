package script.Address;

import datasource.AddressMapper;
import domain.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/updateaddress")
public class UpdateAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAddress() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}
		try {
			int addId = Integer.parseInt(request.getParameter("addId").toString());
			String sendplace = request.getParameter("sendplace");
			String sendman = request.getParameter("sendman");
			String sendphone = request.getParameter("sendphone");
			Address address = new Address(addId, sendplace, sendman, sendphone, userId);
			AddressMapper.updateAddressById(address);
			request.getRequestDispatcher("viewalladdress").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
