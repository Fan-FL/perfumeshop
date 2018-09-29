package script.order;

import controller.FrontCommand;
import service.OrderService;

import javax.servlet.ServletException;
import java.io.IOException;


public class ReceiveProduct extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = null;

    public ReceiveProduct() {
        super();
		orderService = OrderService.getInstance();
    }


	@Override
	public void process() throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum").toString();
		orderService.receiveProduct(orderNum);
		forward("/FrontServlet?module=order&command=ViewMyOrder");
	}
}
