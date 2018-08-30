package script.Cart;

import datasource.CartMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/deletecart")
public class DeleteCart extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public DeleteCart() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cartIds = request.getParameterValues("cartId");
        for(int i = 0;i < cartIds.length;i++){
            int cartId = -1;
            try {
                cartId = Integer.parseInt(cartIds[i]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Exception of cartId casting");
            }
            if (cartId == -1) {
                System.out.println("Failed to get cartId");
            } else if (cartId == 0) {// cartId == 0 means empty cart
                int userId = -1;
                try {
                    userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                } catch (Exception e) {}
                if(userId == -1){
                    response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
                    return;
                }
                CartMapper.deleteCartByUser(userId);
            } else {// delete a record in cart if not 0 and -1
                CartMapper.deleteCartById(cartId);
            }
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
