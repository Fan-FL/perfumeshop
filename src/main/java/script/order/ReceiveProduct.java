package script.order;

import controller.FrontCommand;
import security.AppSession;
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
		if (AppSession.isAuthenticated()) {
			if (AppSession.hasRole(AppSession.USER_ROLE)) {
				String orderNum = request.getParameter("orderNum").toString();
				orderService.receiveProduct(orderNum);
				forward("/FrontServlet?module=order&command=ViewMyOrder");
			} else {
				response.sendError(403);
			}
		} else {
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
		}
	}
}
