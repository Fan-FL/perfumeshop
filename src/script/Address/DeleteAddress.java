package script.Address;

import datasource.AddressMapper;
import domain.Address;
import domain.Pager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/deleteaddress")
public class DeleteAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public DeleteAddress() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int addId = Integer.parseInt(request.getParameter("addId").toString());
            AddressMapper.deleteAddressById(addId);
            request.getRequestDispatcher("viewalladdress").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
