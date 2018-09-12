package script.Cart;

import domain.Cart;
import domain.Product;
import service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@WebServlet("/viewcart")
public class ViewCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public ViewCart() {
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
        }else {
            Map<Cart, Product> cartProductMap = cartService.GetAllCartInfoByUserID(userId);
            request.setAttribute("cartProductMap", cartProductMap);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
