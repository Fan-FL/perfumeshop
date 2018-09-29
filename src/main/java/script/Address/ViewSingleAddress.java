package script.Address;

import controller.FrontCommand;
import domain.Address;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;


public class ViewSingleAddress extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private AddressService addressService = null;

    public ViewSingleAddress() {
        super();
        addressService = AddressService.getInstance();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void process() throws ServletException, IOException {
        int userId = -1;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        } catch (Exception e) {}
        if(userId == -1){
            redirect("login.jsp?responseMsg=userIsNotLogin");
            return;
        }
        int addId = Integer.parseInt(request.getParameter("addId").toString());
        Address address = addressService.viewSingleAddress(userId, addId);
        request.setAttribute("address", address);
        forward("/singleAddress.jsp");
    }
}
