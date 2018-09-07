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
        int currPage = 1;
        try {
            currPage = Integer.parseInt(request.getParameter("currPage"));
        } catch (Exception e) {}
        Pager<Product> pager = this.productService.viewAllProduct(currPage);
        request.setAttribute("pager", pager);
        request.getRequestDispatcher("allProduct.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
