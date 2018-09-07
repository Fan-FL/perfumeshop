package script.order;

import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/deleteorder")
public class DeleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = null;

    public DeleteOrder() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * delete order from order history
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum").toString();
		orderService.deleteOrder(orderNum);
		request.getRequestDispatcher("viewmyorder").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
