package script.Cart;

import controller.FrontCommand;
import net.sf.json.JSONArray;
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
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            redirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String productId = request.getParameter("productId");
            String saleCount = request.getParameter("saleCount");
            int cartId = cartService.addToCart(Integer.parseInt(productId), userId, Integer
                    .parseInt(saleCount));
            int cartCount= cartService.getCartCount(userId);
            String jsonStr = "[{'cartId':'" + cartId + "','cartCount':'"+cartCount +"'}]";
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.flush();
            out.close();
        }
    }
}
