package script.order;

import controller.FrontCommand;
import security.AppSession;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SubmitOrder extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = null;

    public SubmitOrder() {
        super();
		orderService = OrderService.getInstance();
    }

	/**
	 * submit Order, insert orders, modify product store number
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void process() throws ServletException, IOException {
		if (AppSession.isAuthenticated()) {
			if (AppSession.hasRole(AppSession.USER_ROLE)) {
				HttpSession session = request.getSession();
				//deal with resubmit
				String tokenValue = request.getParameter("token");
				String token = (String) session.getAttribute("token");
				if(token != null && token.equals(tokenValue)){
					session.removeAttribute("token");
				}else {
					redirect("repeatCreateOrder.jsp");
					return;
				}
				int addressId = Integer.parseInt(request.getParameter("addressId"));
				String note = request.getParameter("ordernote");
				String url = orderService.submitOrder(AppSession.getId(), addressId, note, request
						.getSession());
				redirect(url);
			} else {
				response.sendError(403);
			}
		} else {
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
		}
	}
}
