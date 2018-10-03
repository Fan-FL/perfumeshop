package script.Cart;

import controller.FrontCommand;
import domain.CartItem;
import domain.Product;
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
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            redirect("login.jsp?responseMsg=userIsNotLogin");
        }else {
            Map<CartItem, Product> cartProductMap = cartService.GetAllCartInfoByUserID(userId);
            request.setAttribute("cartProductMap", cartProductMap);
            forward("/cart.jsp");
        }
    }
}
