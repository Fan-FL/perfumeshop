package script.order;

import datasource.AddressMapper;
import datasource.CartMapper;
import datasource.OrderMapper;
import domain.Address;
import domain.Cart;
import domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/payorder")
public class PayOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public PayOrder() {
        super();
        // TODO Auto-generated constructor stub
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
		int orderStatus = OrderMapper.getOrderStatus(orderNum);
		System.out.println(orderNum);
		if (orderStatus != 0) {
			response.sendRedirect("blank.jsp");
			return;
		}
		OrderMapper.submitPayment(orderNum);
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
