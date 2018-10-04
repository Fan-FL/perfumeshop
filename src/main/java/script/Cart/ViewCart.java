package script.Cart;

import controller.FrontCommand;
import domain.CartItem;
import domain.Product;
import security.AppSession;
import service.CartService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;


public class ViewCart extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public ViewCart() {
        super();
        cartService = CartService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                Map<CartItem, Product> cartProductMap = cartService.GetAllCartInfoByUserID
                        (AppSession.getId());
                request.setAttribute("cartProductMap", cartProductMap);
                forward("/cart.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
