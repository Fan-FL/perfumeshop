package script.Product;

import domain.Pager;
import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/viewallproduct")
public class ViewAllProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

    ProductService productService = null;

    public ViewAllProduct() {
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
        request.getRequestDispatcher("allProduct.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
