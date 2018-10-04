package script.Product;

import controller.FrontCommand;
import domain.Product;
import security.AppSession;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


public class EditProduct extends FrontCommand {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public EditProduct() {
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
                Product product = productService.findById(id);
                request.setAttribute("product", product);
                forward("/back/edit.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            redirect("/back/login.jsp");
        }
    }
}
