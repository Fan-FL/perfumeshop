package script.Address;

import controller.FrontCommand;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;


public class UpdateAddress extends FrontCommand {
	private static final long serialVersionUID = 1L;
	private AddressService addressService = null;

    public UpdateAddress() {
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
		try {
			int addId = Integer.parseInt(request.getParameter("addId").toString());
			String sendplace = request.getParameter("sendplace");
			String sendman = request.getParameter("sendman");
			String sendphone = request.getParameter("sendphone");
			addressService.updateAddress(addId, sendplace, sendman, sendphone, userId);
			forward("/FrontServlet?module=Address&command=ViewAllAddress");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
