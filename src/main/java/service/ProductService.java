package service;

import datasource.LockingMapper;
import datasource.ProductMapper;
import domain.Product;

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

    public void addProduct(Product product){
        new LockingMapper(new ProductMapper()).insert(product);
    }

    public void updateProduct(Product product){
        new LockingMapper(new ProductMapper()).update(product);
    }

    public void deleteProduct(int id){
        Product product = new Product();
        product.setId(id);
        new LockingMapper(new ProductMapper()).delete(product);
    }

    public Product findById(int id) {
        return (Product) new LockingMapper(new ProductMapper()).findById(id);
    }
}
