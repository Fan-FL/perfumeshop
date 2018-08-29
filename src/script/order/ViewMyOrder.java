package script.order;

import datasource.OrderMapper;
import domain.OrderMsg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/viewmyorder")
public class ViewMyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public ViewMyOrder() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			response.sendRedirect("blank.jsp");
			return;
		}
		List<OrderMsg> ordermsg = OrderMapper.getOrderMsgs(userId);
		request.setAttribute("ordermsg", ordermsg);
		request.getRequestDispatcher("accountorder.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
