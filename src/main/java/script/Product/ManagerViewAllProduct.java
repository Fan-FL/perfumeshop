package script.Product;

import controller.FrontCommand;
import domain.Product;
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
        List<Product> products = this.productService.viewAllProduct();
        request.setAttribute("products", products);
        forward("/back/allProduct.jsp");
    }
}
