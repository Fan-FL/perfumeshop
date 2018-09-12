package service;

import datasource.ProductMapper;
import domain.Product;

import java.util.List;

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
}
