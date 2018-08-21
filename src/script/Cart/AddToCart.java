package script.Cart;

import datasource.CartMapper;
import domain.Cart;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String productId = request.getParameter("productId");
            String saleCount = request.getParameter("saleCount");
            Cart cart = new Cart();
            cart.setProductId(Integer.parseInt(productId));
            cart.setUserId(userId);
            cart.setSaleCount(Integer.parseInt(saleCount));
            int cartId = CartMapper.insert(cart);
            int cartCount= CartMapper.getCartCount(userId);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
