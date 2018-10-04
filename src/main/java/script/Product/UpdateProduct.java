package script.Product;

import controller.FrontCommand;
import domain.Product;
import security.AppSession;
import service.ProductService;

import javax.servlet.ServletException;
import java.io.IOException;


public class UpdateProduct extends FrontCommand {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public UpdateProduct() {
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
            if (AppSession.hasRole(AppSession.MANAGER_ROLE)) {
                int id = Integer.parseInt(request.getParameter("productId"));
                String productName = request.getParameter("productName");
                double productPrice = Double.parseDouble(request.getParameter("productPrice"));
                int storeNum = Integer.parseInt(request.getParameter("storeNum"));
                String productDesc = request.getParameter("productDesc");
                Product product = new Product(id, productName, productPrice, productDesc,
                        "upload/64a34e68-d96f-412b-b82c-40a232c85d7d.jpg", storeNum,
                        1);
                productService.updateProduct(product);
                forward("/FrontServlet?module=Product&command=ManagerViewAllProduct");
            } else {
            response.sendError(403);
            }
        } else {
            redirect("/back/login.jsp");
        }
    }
}
