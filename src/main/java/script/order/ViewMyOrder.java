package script.order;

import controller.FrontCommand;
import domain.OrderMsg;
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
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			redirect("blank.jsp");
			return;
		}
		List<OrderMsg> ordermsg = orderService.viewMyOrder(userId);
		request.setAttribute("ordermsg", ordermsg);
		forward("/accountorder.jsp");
	}
}
