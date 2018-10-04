package script.Product;

import controller.FrontCommand;
import domain.Product;
import security.AppSession;
import service.ProductService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


public class ManagerViewAllProduct extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ProductService productService = null;

    public ManagerViewAllProduct() {
        super();
        productService = ProductService.getInstance();
    }

    /**
     * view all products by paging
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.MANAGER_ROLE)) {
                List<Product> products = this.productService.viewAllProduct();
                request.setAttribute("products", products);
                forward("/back/allProduct.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            redirect("/back/login.jsp");
        }
    }
}
