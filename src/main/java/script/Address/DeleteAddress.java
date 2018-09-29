package script.Address;

import controller.FrontCommand;
import service.AddressService;

import javax.servlet.ServletException;
import java.io.IOException;


public class DeleteAddress extends FrontCommand {
	private static final long serialVersionUID = 1L;
    private AddressService addressService = null;

    public DeleteAddress() {
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
            addressService.deleteAddress(addId, userId);
            forward("/FrontServlet?module=Address&command=ViewAllAddress");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
