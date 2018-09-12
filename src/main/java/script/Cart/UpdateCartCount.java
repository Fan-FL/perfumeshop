package script.Cart;

import service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/updatecartcount")
public class UpdateCartCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartService cartService = null;

    public UpdateCartCount() {
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
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int saleCount = Integer.parseInt(request.getParameter("saleCount"));
        cartService.updateCartCount(userId, cartId, saleCount);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
