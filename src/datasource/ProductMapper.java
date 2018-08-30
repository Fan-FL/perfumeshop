package datasource;

import domain.Pager;
import domain.Product;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMapper {
    private static Map<Integer, Product> productMap = new HashMap<>();

    public void put(int id, Product product){
        productMap.put(id, product);
    }

    public Product get(int id){
        return productMap.get(id);
    }

    public static Product getProduct(int productId) {
        Product product = productMap.get(productId);
        if (product != null){
            System.out.println("Load from map.");
            return product;
        }

        String sql = "SELECT PRODUCT_ID ,PRODUCT_NAME ,PRODUCT_PRICE ,STORE_NUM ,PRODUCT_IMAGE_PATH," +
                " PRODUCT_DESC ,PRODUCT_STATUS " +
                "FROM product " +
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
                productMap.put(productId, product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return product;
    }

    public Pager<Product> getProductPager(int currPage, int pageSize, Product product) {
        String sqlForDataCount = null;
        String sqlForData = null;
        String searchKey = product.getProductName();

        boolean flag = false;
        if(searchKey.equals("%%") || searchKey == null){
            sqlForDataCount = "SELECT COUNT(*) FROM product WHERE PRODUCT_STATUS=1";
            sqlForData = "SELECT PRODUCT_ID productId,PRODUCT_NAME productName,"
                    + "PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_STATUS productStatus "
                    + "FROM product WHERE PRODUCT_STATUS=1";
            flag = true;
        }else {
            sqlForDataCount = "SELECT COUNT(*) FROM product WHERE PRODUCT_STATUS=1 AND PRODUCT_NAME LIKE ?";
            sqlForData = "SELECT PRODUCT_ID productId,PRODUCT_NAME productName,"
                    + "PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_STATUS productStatus "
                    + "FROM product WHERE PRODUCT_STATUS=1 AND PRODUCT_NAME LIKE ?";
        }
        if(flag){
            return new PagerHandler().getPager(Product.class, sqlForDataCount, sqlForData, currPage,
                    pageSize);
        }
        return new PagerHandler().getPager(Product.class, sqlForDataCount, sqlForData, currPage, pageSize,
                searchKey);
    }

    public static void changeProductStock(int productId, int saleCount) {
        String selectSql = "SELECT (STORE_NUM-?) FROM product WHERE PRODUCT_ID=?";
        Object storeNum = DBHelper.getValue(selectSql, saleCount,productId);
        String sql = "UPDATE product SET STORE_NUM=? WHERE PRODUCT_ID=?";
        DBHelper.update(sql, storeNum,productId);

        Product product = productMap.get(productId);
        if (product != null){
            product.setStoreNum((Integer)storeNum);
        }
    }

}
