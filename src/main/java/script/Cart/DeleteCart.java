package script.Cart;

import controller.FrontCommand;
import domain.Address;
import security.AppSession;
import service.CartService;

import javax.servlet.ServletException;
import java.io.IOException;


public class DeleteCart extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public DeleteCart() {
        super();
        cartService = CartService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                String[] cartIds = request.getParameterValues("cartId");
                for(int i = 0;i < cartIds.length;i++){
                    int cartId = -1;
                    try {
                        cartId = Integer.parseInt(cartIds[i]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Exception of cartId casting");
                    }
                    if (cartId == -1) {
                        System.out.println("Failed to get cartId");
                    } else if (cartId == 0) {// cartId == 0 means empty cart
                        cartService.deleteCartByUser(AppSession.getId());
                    } else {// delete a record in cart if not 0 and -1
                        cartService.deleteCartItemById(AppSession.getId(), cartId);
                    }
                }
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }


    }
}
