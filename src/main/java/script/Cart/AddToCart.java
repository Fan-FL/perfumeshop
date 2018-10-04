package script.Cart;

import controller.FrontCommand;
import domain.Address;
import net.sf.json.JSONArray;
import security.AppSession;
import service.CartService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;


public class AddToCart extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public AddToCart() {
        super();
        cartService = CartService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                PrintWriter out = null;
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    out = response.getWriter();
                    String productId = request.getParameter("productId");
                    String saleCount = request.getParameter("saleCount");
                    int cartId = cartService.addToCart(Integer.parseInt(productId), AppSession.getId(),
                            Integer
                            .parseInt(saleCount));
                    int cartCount= cartService.getCartCount(AppSession.getId());
                    String jsonStr = "[{'cartId':'" + cartId + "','cartCount':'"+cartCount +"'}]";
                    JSONArray json = JSONArray.fromObject(jsonStr);
                    out.write(json.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    out.flush();
                    out.close();
                }
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
