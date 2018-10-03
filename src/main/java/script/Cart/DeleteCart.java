package script.Cart;

import controller.FrontCommand;
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
        String[] cartIds = request.getParameterValues("cartId");
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            redirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
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
                cartService.deleteCartByUser(userId);
            } else {// delete a record in cart if not 0 and -1
                cartService.deleteCartItemById(userId, cartId);
            }
        }
    }
}
