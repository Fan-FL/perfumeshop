package script.order;

import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/receiveproduct")
public class ReceiveProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = null;

    public ReceiveProduct() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * receive product (product delivered)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum").toString();
		orderService.receiveProduct(orderNum);
		request.getRequestDispatcher("viewmyorder").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
