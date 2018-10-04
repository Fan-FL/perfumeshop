package script.Product;

import controller.FrontCommand;
import domain.Product;
import security.AppSession;
import service.ProductService;

import javax.servlet.ServletException;
import java.io.IOException;


public class ViewProductDetail extends FrontCommand {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public ViewProductDetail() {
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
            if (AppSession.hasRole(AppSession.USER_ROLE)) {
                int productId = 0;
                try {
                    productId = Integer.parseInt(request.getParameter("productid"));
                } catch (Exception e) {
                    System.out.println("failed to get product ID");
                    return;
                }
                Product productInfo = productService.findById(productId);
                request.setAttribute("product", productInfo);
                forward("/singleProduct.jsp");
            } else {
                response.sendError(403);
            }
        } else {
            response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
        }


    }
}
