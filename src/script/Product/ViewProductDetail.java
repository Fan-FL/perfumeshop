package script.Product;

import datasource.ProductMapper;
import domain.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/viewproductdetail")
public class ViewProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public ViewProductDetail() {
        super();
        productService = ProductService.getInstance();
    }

    /**
     * get a product's detail
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("productid"));
        } catch (Exception e) {
            System.out.println("failed to get product ID");
        }
        Product productInfo = productService.viewProductDetail(productId);
        request.setAttribute("product", productInfo);
        request.getRequestDispatcher("singleProduct.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
