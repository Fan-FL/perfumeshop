package script.Cart;

import net.sf.json.JSONArray;
import service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public AddToCart() {
        super();
        cartService = CartService.getInstance();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
