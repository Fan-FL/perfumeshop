package script.Address;

import datasource.AddressMapper;
import domain.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/viewsingleaddress")
public class ViewSingleAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public ViewSingleAddress() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int addId = Integer.parseInt(request.getParameter("addId").toString());
        Address address = AddressMapper.getAddressById(addId);
        request.setAttribute("address", address);
        request.getRequestDispatcher("singleAddress.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
