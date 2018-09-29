package script.order;

import controller.FrontCommand;
import service.OrderService;

import javax.servlet.ServletException;
import java.io.IOException;


public class PayOrder extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = null;

    public PayOrder() {
        super();
		orderService = OrderService.getInstance();
    }

	/**
	 * pay Order
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void process() throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum");
		int orderStatus = orderService.getOrderStatus(orderNum);
		System.out.println(orderNum);
		if (orderStatus != 0) {
			redirect("blank.jsp");
			return;
		}
		orderService.submitPayment(orderNum);
		request.getSession().removeAttribute("totalPrice");
		request.getSession().removeAttribute("cartProductMap");
		request.getSession().removeAttribute("address");
		request.getSession().removeAttribute("orderNum");
		redirect("payment_success.jsp");
	}
}
