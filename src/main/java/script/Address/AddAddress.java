package script.Address;

import controller.FrontCommand;
import domain.User;
import security.AppSession;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;


public class AddAddress extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private AddressService addressService = null;

    public AddAddress() {
        super();
		addressService = AddressService.getInstance();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void process() throws ServletException, IOException {
		if (AppSession.isAuthenticated()) {
			if (AppSession.hasRole(AppSession.USER_ROLE)) {
				String sendplace = request.getParameter("sendplace");
				String sendman = request.getParameter("sendman");
				String sendphone = request.getParameter("sendphone");
				addressService.addAddress(sendplace, sendman, sendphone, AppSession.getId());
				forward("/FrontServlet?module=Address&command=ViewAllAddress");
			} else {
				response.sendError(403);
			}
		} else {
			response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
		}
	}
}
