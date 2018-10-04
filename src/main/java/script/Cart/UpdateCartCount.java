package script.Cart;

import controller.FrontCommand;
import domain.Address;
import security.AppSession;
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
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                int cartId = Integer.parseInt(request.getParameter("cartId"));
                int saleCount = Integer.parseInt(request.getParameter("saleCount"));
                cartService.updateCartCount(AppSession.getId(), cartId, saleCount);
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
