package script.order;

import controller.FrontCommand;
import domain.OrderMsg;
import security.AppSession;
import service.OrderService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


public class ViewMyOrder extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = null;

    public ViewMyOrder() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * view my Order history
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void process() throws ServletException, IOException {
		if (AppSession.isAuthenticated()) {
			if (AppSession.hasRole(AppSession.USER_ROLE)) {
				List<OrderMsg> ordermsg = orderService.viewMyOrder(AppSession.getId());
				request.setAttribute("ordermsg", ordermsg);
				forward("/accountorder.jsp");
			} else {
				response.sendError(403);
			}
		} else {
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
		}
	}
}
