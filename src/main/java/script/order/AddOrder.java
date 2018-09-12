package script.order;

import datasource.CartMapper;
import datasource.UserMapper;
import domain.Address;
import domain.Cart;
import domain.Product;
import domain.User;
import service.AddressService;
import service.CartService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/addorder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = null;

    public AddOrder() {
        super();
		orderService = OrderService.getInstance();
    }


	/**
	 * Get cart info and prepare data for createOrder page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}
		orderService.addOrder(userId, request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
