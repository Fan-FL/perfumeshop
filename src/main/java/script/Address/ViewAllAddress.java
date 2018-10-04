package script.Address;

import controller.FrontCommand;
import domain.Address;
import security.AppSession;
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
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                List<Address> addresses = addressService.viewAllAddress(AppSession.getId());
                request.setAttribute("addresses", addresses);
                forward("/addressManage.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
