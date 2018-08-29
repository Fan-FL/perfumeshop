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
		//处理表单的重复提交start
		String tokenValue = request.getParameter("token");
		String token = (String) session.getAttribute("token");
		if(token != null && token.equals(tokenValue)){
			session.removeAttribute("token");
		}else {
			response.sendRedirect("repeatCreateOrder.jsp");
			return;
		}
		//处理表单的重复提交end
		Map<Cart,Product> cartProductMap = (Map<Cart, Product>)session.getAttribute("cartProductMap");
		String[] cartIds = (String[]) request.getSession().getAttribute("cartIds");
		int orderId = -1;
		String orderNum = null;


		try {
			int addressId = Integer.parseInt(request.getParameter("addressId"));
			Address address = AddressMapper.getAddressById(addressId);
			String note = request.getParameter("ordernote");
			// 生成订单 创建orders 表
			Timestamp orderTime = new Timestamp(System.currentTimeMillis());
			Random random = new Random();
			String ran = random.nextInt(10)*1000+random.nextInt(10)*100+random.nextInt(10)*10+random.nextInt(10) + "";
			System.out.println(ran);
			orderNum = System.currentTimeMillis() + ran + userId;
			System.out.println(orderNum);
			double totalPrice = 0;//遍历cartProductMap 进行orders表的创建 同时生成订单总价
			int orderStatus = 0;//表示已下单
			List<Order> orders = new ArrayList<Order>();
			for(Map.Entry<Cart, Product> cartProduct :cartProductMap.entrySet()){
				//根据Id获取商品，用于验证库存和商品状态
				Product product = ProductMapper.getProduct(cartProduct.getValue().getProductId());
				int saleCount = cartProduct.getKey().getSaleCount();
				int storeNum = product.getStoreNum();
				int productStatus = product.getProductStatus();
				if(productStatus==0){
					response.sendRedirect("productSoldOut.jsp");
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
			//库存验证通过再进行Orders表的添加
			for(Order order:orders){
				orderId = OrderMapper.addOrders(order);
			}
			if(orderId == -1){
				throw new RuntimeException("订单生成失败");
			}
			session.setAttribute("totalPrice", totalPrice);
			session.setAttribute("orderNum", orderNum);
			session.setAttribute("address", address);
		} catch (Exception e) {
			throw new RuntimeException("订单生成时发生异常");
		}
		if(orderId != -1 && orderNum != null){
			// 订单生成过程未发生异常 则 删除购物车中已经下单的商品
			CartMapper.deleteCartByUserCart(userId,cartIds);
			request.getSession().removeAttribute("buyNowCart");
			//同时减商品库存
			//1)通过订单编号获取购买的商品ID和购买的数量
			List<Cart> boughtProducts = CartMapper.getCartsByOrderNum(orderNum);
			//2)根据购买的信息，修改数据库商品数量
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
