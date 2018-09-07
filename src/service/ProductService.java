package service;

import datasource.ProductMapper;
import domain.ConfigProperties;
import domain.Pager;
import domain.Product;

public class ProductService {
    private static ProductService instance;
    static {
        instance = new ProductService();
    }
    public static ProductService getInstance() {
        return instance;
    }

    private ProductService(){}

    public Pager<Product> viewAllProduct(int currPage){
        int productPageSize = ConfigProperties.allProductPageSize;
        return ProductMapper.getProductPager(currPage, productPageSize);
    }

    public Product viewProductDetail(int productId){
        return ProductMapper.findById(productId);
    }
}
