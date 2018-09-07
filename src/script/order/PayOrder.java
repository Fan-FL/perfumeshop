package script.order;

import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/payorder")
public class PayOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = null;

    public PayOrder() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * pay order
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum");
		int orderStatus = orderService.getOrderStatus(orderNum);
		System.out.println(orderNum);
		if (orderStatus != 0) {
			response.sendRedirect("blank.jsp");
			return;
		}
		orderService.submitPayment(orderNum);
		request.getSession().removeAttribute("totalPrice");
		request.getSession().removeAttribute("cartProductMap");
		request.getSession().removeAttribute("address");
		request.getSession().removeAttribute("orderNum");
		response.sendRedirect("payment_success.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
