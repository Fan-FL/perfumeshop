package datasource;

import domain.DomainObject;
import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements IMapper{

    @Override
    public Product findById(int productId) {
        Product product = IdentityMap.productMap.get(productId);
        if (product != null){
            System.out.println("Load from map.");
            return product;
        }

        String sql = "SELECT PRODUCT_ID ,PRODUCT_NAME ,PRODUCT_PRICE ,STORE_NUM ,PRODUCT_IMAGE_PATH," +
                " PRODUCT_DESC ,PRODUCT_STATUS " +
                "FROM perfume.product " +
                "WHERE PRODUCT_ID=?";
        PreparedStatement ps = null;
        ResultSet rs  = null;

        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()){
                String productName = rs.getString(2);
                double productPrice = rs.getDouble(3);
                int storeNum = rs.getInt(4);
                String productImagePath = rs.getString(5);
                String productDesc = rs.getString(6);
                int productStatus = rs.getInt(7);
                product = new Product(productId, productName, productPrice, productDesc,
                        productImagePath, storeNum, productStatus);
                IdentityMap.productMap.put(productId, product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        if(product != null){
            return product;
        }else {
            return null;
        }
    }

    public static List<Product> getAllProducts() {
        String sql = "SELECT PRODUCT_ID, PRODUCT_NAME, STORE_NUM, "
                + "PRODUCT_PRICE, PRODUCT_IMAGE_PATH, " +
                "PRODUCT_DESC, " +
                "PRODUCT_STATUS "
                + "FROM perfume.product";

        PreparedStatement ps = null;
        ResultSet rs  = null;
        List<Product> products = new ArrayList<Product>();
        try {
            ps = DBConnection.prepare(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int productId = rs.getInt(1);
                String productName = rs.getString(2);
                int storeNum = rs.getInt(3);
                double productPrice = rs.getDouble(4);
                String productImagePath = rs.getString(5);
                String productDesc = rs.getString(6);
                int productStatus = rs.getInt(7);
                Product product = IdentityMap.productMap.get(productId);
                if(product == null){
                    product = new Product(productId, productName, productPrice, productDesc,
                            productImagePath, storeNum, productStatus);
                    IdentityMap.productMap.put(productId, product);
                }
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }

        return products;
    }

    @Override
    public int insert(DomainObject obj) {
        Product product = (Product)obj;
        String sql = "insert into perfume.product (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESC,"
                + "PRODUCT_IMAGE_PATH,STORE_NUM,PRODUCT_STATUS) values (?,?,?,?,?,?)";
        int productId = DBHelper.updateGetGeneratedKeys(sql, product.getProductName(), product
                        .getProductPrice(), product.getProductDesc(),product.getProductImagePath
                (),product.getStoreNum(),product.getProductStatus()
        );
        product.setId(productId);
        IdentityMap.productMap.put(productId, product);
        return productId;
    }

    @Override
    public void update(DomainObject obj) {
        Product product = (Product)obj;
        String sql = "UPDATE perfume.product SET PRODUCT_NAME=?,PRODUCT_PRICE=?,PRODUCT_DESC=?,"
                + "PRODUCT_IMAGE_PATH=?,STORE_NUM=?,PRODUCT_STATUS=? WHERE PRODUCT_ID=?";
        DBHelper.update(sql,product.getProductName(),product.getProductPrice(),product.getProductDesc(),
                product.getProductImagePath(),product.getStoreNum(),product.getProductStatus(),
                product.getId());
        Product inMap = IdentityMap.productMap.get(product.getId());
        inMap.setStoreNum(product.getStoreNum());
        inMap.setProductDesc(product.getProductDesc());
        inMap.setProductImagePath(product.getProductImagePath());
        inMap.setProductName(product.getProductName());
        inMap.setProductPrice(product.getProductPrice());
        inMap.setProductStatus(product.getProductStatus());
    }

    @Override
    public void delete(DomainObject obj) {
        Product product = (Product)obj;
        String sql = "DELETE FROM perfume.product WHERE PRODUCT_ID=?";
        DBHelper.update(sql, product.getId());
        IdentityMap.productMap.remove(product.getId());
    }
}
