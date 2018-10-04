package script.User;

import controller.FrontCommand;
import security.AppSession;
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
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                int cartCount = CartService.getInstance().getCartCount(AppSession.getId());
                request.setAttribute("cartCount", cartCount);
            } else {
                response.sendError(403);
            }
        }
        include("/header.jsp");
    }
}
