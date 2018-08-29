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


@WebServlet("/deletecart")
public class DeleteCart extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public DeleteCart() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cartIds = request.getParameterValues("cartId");
        System.out.println("1111111111");
        for(int i = 0;i < cartIds.length;i++){
            int cartId = -1;
            try {
                cartId = Integer.parseInt(cartIds[i]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("cartId类型转换异常");
            }
            if (cartId == -1) {
                System.out.println("未获得页面传过来的cartId");
            } else if (cartId == 0) {// cartId == 0 标记为清空购物车
                int userId = -1;
                try {
                    userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                } catch (Exception e) {}
                if(userId == -1){
                    response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
                    return;
                }
                CartMapper.deleteCartByUser(userId);
            } else {// 不为0 也不是 -1 即 删除购物车中的一条数据
                CartMapper.deleteCartById(cartId);
            }
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
