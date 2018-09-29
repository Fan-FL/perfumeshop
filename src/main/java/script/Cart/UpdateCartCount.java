package script.Cart;

import controller.FrontCommand;
import service.CartService;

import javax.servlet.ServletException;
import java.io.IOException;


public class UpdateCartCount extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public UpdateCartCount() {
        super();
        cartService = CartService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            redirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int saleCount = Integer.parseInt(request.getParameter("saleCount"));
        cartService.updateCartCount(userId, cartId, saleCount);
    }
}
