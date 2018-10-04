package script.Product;

import controller.FrontCommand;
import security.AppSession;
import service.ProductService;

import javax.servlet.ServletException;
import java.io.IOException;


public class DeleteProduct extends FrontCommand {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public DeleteProduct() {
        super();
        productService = ProductService.getInstance();
    }

    /**
     * get a product's detail
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void process() throws ServletException, IOException {
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(AppSession.MANAGER_ROLE)){
                int id = Integer.parseInt(request.getParameter("productId"));
                productService.deleteProduct(id);
                forward("/FrontServlet?module=Product&command=ManagerViewAllProduct");
            } else {
                response.sendError(403);
            }
        } else {
            redirect("/back/login.jsp");
        }
    }
}
