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


@WebServlet("/addproduct")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public AddProduct() {
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
        if(request.getSession().getAttribute("manager") == null){
            response.sendRedirect("/back/login.jsp");
            return;
        }
        productService.addProduct(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
