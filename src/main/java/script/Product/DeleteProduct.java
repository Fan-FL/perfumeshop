package script.Product;

import controller.FrontCommand;
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
        if(request.getSession().getAttribute("manager") == null){
            redirect("/managerLogin.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            productService.deleteProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        forward("/FrontServlet?module=Product&command=ManagerViewAllProduct");
    }
}
