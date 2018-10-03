package script.order;

import controller.FrontCommand;
import domain.Address;
import domain.CartItem;
import domain.Product;
import domain.User;
import service.OrderService;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOrder extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = null;
	private UserService userService = null;

    public AddOrder() {
        super();
		orderService = OrderService.getInstance();
		userService = UserService.getInstance();
    }

	/**
	 * Get cart info and prepare data for createOrder page
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
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}

		User user = userService.findUserById(userId);
		List<Address> addresses = user.getDeliveryAddresses();
		request.setAttribute("addresses", addresses);
		if(user.getCartItems().isEmpty()){
			forward("/FrontServlet?module=Cart&command=ViewCart");
		}else{
			Map<CartItem, Product> cartProductMap = new HashMap<CartItem, Product>();
			if (!user.getCartItems().isEmpty()){
				for (CartItem cartItem : user.getCartItems()){
					cartProductMap.put(cartItem, cartItem.getProduct());
				}
			}
			request.getSession().setAttribute("cartProductMap", cartProductMap);
			forward("/createOrder.jsp");
		}
	}
}
