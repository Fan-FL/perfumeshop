package script.order;

import datasource.AddressMapper;
import datasource.CartMapper;
import datasource.OrderMapper;
import datasource.ProductMapper;
import domain.Address;
import domain.Cart;
import domain.Order;
import domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@WebServlet("/submitorder")
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public SubmitOrder() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * submit order, insert orders, modify product store number
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			return;
		}
		//deal with resubmit
		String tokenValue = request.getParameter("token");
		String token = (String) session.getAttribute("token");
		if(token != null && token.equals(tokenValue)){
			session.removeAttribute("token");
		}else {
			response.sendRedirect("repeatCreateOrder.jsp");
			return;
		}

		Map<Cart,Product> cartProductMap = (Map<Cart, Product>)session.getAttribute("cartProductMap");
		String[] cartIds = (String[]) request.getSession().getAttribute("cartIds");
		int orderId = -1;
		String orderNum = null;

		try {
			int addressId = Integer.parseInt(request.getParameter("addressId"));
			Address address = AddressMapper.getAddressById(addressId);
			String note = request.getParameter("ordernote");
			Timestamp orderTime = new Timestamp(System.currentTimeMillis());
			Random random = new Random();
			String ran = random.nextInt(10)*1000+random.nextInt(10)*100+random.nextInt(10)*10+random.nextInt(10) + "";
			System.out.println(ran);
			orderNum = System.currentTimeMillis() + ran + userId;
			System.out.println(orderNum);
			// total price of order
			double totalPrice = 0;
			// 0 represent order created
			int orderStatus = 0;
			List<Order> orders = new ArrayList<Order>();
			for(Map.Entry<Cart, Product> cartProduct :cartProductMap.entrySet()){
				// Get product by product id for checking inventory and product status
				Product product = ProductMapper.getProduct(cartProduct.getValue().getProductId());
				int saleCount = cartProduct.getKey().getSaleCount();
				int storeNum = product.getStoreNum();
				int productStatus = product.getProductStatus();
				if(productStatus==0){
					response.sendRedirect("productOffShelf.jsp");
					return;
				}
				if(saleCount > storeNum){
					response.sendRedirect("outOfStockNum.jsp");
					return;
				}
				Order order = new Order(orderNum, orderTime, orderStatus, note, userId,
						address.getSendPlace(),address.getSendMan(), address.getSendPhone(),
						cartProduct.getValue().getProductId(), cartProduct.getValue().getProductName(),
						cartProduct.getValue().getProductPrice(), cartProduct.getKey().getSaleCount());
				orders.add(order);
				totalPrice += cartProduct.getValue().getProductPrice()*cartProduct.getKey().getSaleCount();
			}
			//Inventory checking passed, create orders at this time
			for(Order order:orders){
				orderId = OrderMapper.addOrders(order);
			}
			if(orderId == -1){
				throw new RuntimeException("Failed to create order");
			}
			session.setAttribute("totalPrice", totalPrice);
			session.setAttribute("orderNum", orderNum);
			session.setAttribute("address", address);
		} catch (Exception e) {
			throw new RuntimeException("Error in order creation");
		}
		if(orderId != -1 && orderNum != null){
			// delete products in cart
			CartMapper.deleteCartByUserAndCartIds(userId,cartIds);
			request.getSession().removeAttribute("buyNowCart");
			// decrease store number
			// get product ID and purchased quantity by order number
			List<Cart> boughtProducts = CartMapper.getCartsByOrderNum(orderNum);
			// modify sotre number
			for(Cart bought:boughtProducts){
				int productId = bought.getProductId();
				int saleCount = bought.getSaleCount();
				ProductMapper.changeProductStock(productId,saleCount);
			}
			response.sendRedirect("order_create_success.jsp");
			return;
		}
		response.sendRedirect("order_create_fail.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
