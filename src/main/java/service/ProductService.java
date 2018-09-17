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

    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String productName = request.getParameter("productName");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int storeNum = Integer.parseInt(request.getParameter("storeNum"));
            String productDesc = request.getParameter("productDesc");
            Product product = new Product(productName, productPrice, productDesc,
                    "upload/64a34e68-d96f-412b-b82c-40a232c85d7d.jpg", storeNum,
                    1);
            new ProductMapper().insert(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/manager_viewallproduct").forward(request, response);
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

    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int storeNum = Integer.parseInt(request.getParameter("storeNum"));
            String productDesc = request.getParameter("productDesc");
            Product product = new Product(id, productName, productPrice, productDesc,
                    "upload/64a34e68-d96f-412b-b82c-40a232c85d7d.jpg", storeNum,
                    1);
            new ProductMapper().update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/manager_viewallproduct").forward(request, response);
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            Product product = new Product();
            product.setId(id);
            new ProductMapper().delete(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/manager_viewallproduct").forward(request, response);
    }
}
