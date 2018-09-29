package script.order;

import controller.FrontCommand;
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
		HttpSession session = request.getSession();
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			redirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}
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
		String url = orderService.submitOrder(userId, addressId, note, request.getSession());
		redirect(url);
	}
}
