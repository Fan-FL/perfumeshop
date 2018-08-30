package script.Cart;

import datasource.CartMapper;
import domain.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/updatecartcount")
public class UpdateCartCount extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UpdateCartCount() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int saleCount = Integer.parseInt(request.getParameter("saleCount"));
        Cart cart = CartMapper.getCart(cartId);
        // change sale count
        cart.setSaleCount(saleCount);
        CartMapper.updateCart(cart);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
