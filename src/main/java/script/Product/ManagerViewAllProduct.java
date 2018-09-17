package script.Product;

import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/manager_viewallproduct")
public class ManagerViewAllProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

    ProductService productService = null;

    public ManagerViewAllProduct() {
        super();
        productService = ProductService.getInstance();
    }

    /**
     * view all products by paging
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = this.productService.viewAllProduct();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/back/Product/index.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
