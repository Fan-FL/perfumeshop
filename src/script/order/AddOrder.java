package script.order;

import datasource.AddressMapper;
import datasource.CartMapper;
import domain.Address;
import domain.Cart;
import domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/addorder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public AddOrder() {
        super();
        // TODO Auto-generated constructor stub
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
		List<Address> addresses = AddressMapper.getAddressWithUserId(userId);
		request.setAttribute("addresses", addresses);
		String[] cartIds = request.getParameterValues("cartId");
		if(cartIds.length ==0){
			request.getRequestDispatcher("viewcart").forward(request, response);
			return;
		}
		Map<Cart, Product> cartProductMap = CartMapper.getCartProductMap(userId,cartIds);
		request.getSession().setAttribute("cartProductMap", cartProductMap);
		request.getSession().setAttribute("cartIds", cartIds);
		request.getRequestDispatcher("createOrder.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
