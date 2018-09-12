package script.order;

import domain.Cart;
import domain.Product;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@WebServlet("/submitorder")
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = null;

    public SubmitOrder() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * submit order, insert orders, modify product store number
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}
		//deal with resubmit
		String tokenValue = request.getParameter("token");
		String token = (String) session.getAttribute("token");
		if(token != null && token.equals(tokenValue)){
			session.removeAttribute("token");
		}else {
			response.sendRedirect("repeatCreateOrder.jsp");
			return;
		}

		int addressId = Integer.parseInt(request.getParameter("addressId"));
		String note = request.getParameter("ordernote");
		String url = orderService.submitOrder(userId, addressId, note, request.getSession());
		response.sendRedirect(url);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
