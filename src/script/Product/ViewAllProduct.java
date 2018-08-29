package script.Product;

import datasource.ProductMapper;
import domain.ConfigProperties;
import domain.Pager;
import domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/viewallproduct")
public class ViewAllProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductMapper productMapper = new ProductMapper();


    public ViewAllProduct() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currPage = 1;
        try {
            currPage = Integer.parseInt(request.getParameter("currPage"));
            System.out.println(currPage);
        } catch (Exception e) {}
        Product product = new Product(null, 0, null, null, 0, 0);
        int productPageSize = ConfigProperties.allProductPageSize;
        Pager<Product> pager = this.productMapper.getProductPager(currPage, productPageSize,
                product);
        System.out.println(pager);
        request.setAttribute("pager", pager);
        request.getRequestDispatcher("allProduct.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.doGet(request, response);
	}

}
