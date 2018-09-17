package script.Product;

import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/editproduct")
public class EditProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;


    ProductService productService = null;

    public EditProduct() {
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
        productService.editProduct(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
