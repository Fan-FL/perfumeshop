package script.Address;

import controller.FrontCommand;
import domain.Address;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


public class ViewAllAddress extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private AddressService addressService = null;

    public ViewAllAddress() {
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
        List<Address> addresses = addressService.viewAllAddress(userId);
        request.setAttribute("addresses", addresses);
        forward("/addressManage.jsp");
    }
}
