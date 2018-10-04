package script.Address;

import controller.FrontCommand;
import domain.Address;
import security.AppSession;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


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
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                int addId = Integer.parseInt(request.getParameter("addId").toString());
                Address address = addressService.viewSingleAddress(AppSession.getId(), addId);
                request.setAttribute("address", address);
                forward("/singleAddress.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }
    }
}
