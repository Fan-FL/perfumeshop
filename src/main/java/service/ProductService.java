package service;

import datasource.ProductMapper;
import domain.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class ProductService {
    private static ProductService instance;
    static {
        instance = new ProductService();
    }
    public static ProductService getInstance() {
        return instance;
    }

    private ProductService(){}

    public List<Product> viewAllProduct(){
        return ProductMapper.getAllProducts();
    }

    public Product viewProductDetail(int productId){
        return ProductMapper.findById(productId);
    }

    public void addProduct(Product product){
        new ProductMapper().insert(product);
    }

    public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            Product product = ProductMapper.findById(id);
            request.setAttribute("product", product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/back/Product/edit.jsp").forward(request, response);
    }

    public void updateProduct(Product product){
        new ProductMapper().update(product);
    }

    public void deleteProduct(int id){
        Product product = new Product();
        product.setId(id);
        new ProductMapper().delete(product);
    }

    public Product findById(int id) {
        return ProductMapper.findById(id);
    }
}
