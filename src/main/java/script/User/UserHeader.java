package script.User;

import controller.FrontCommand;
import service.CartService;
import service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class UserHeader extends FrontCommand {
	private static final long serialVersionUID = 1L;

    private UserService userService = null;

    public UserHeader() {
        super();
        userService = UserService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        int userId = -1;
        try {
            userId = (Integer) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            System.out.println("user hasn't logged in!");
        }
        if(userId != -1){
            int cartCount = CartService.getInstance().getCartCount(userId);
            request.setAttribute("cartCount", cartCount);
        }
        include("/header.jsp");
    }
}
