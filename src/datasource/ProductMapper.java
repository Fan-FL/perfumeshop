package datasource;

import domain.DomainObject;
import domain.Pager;
import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements IMapper{

    public static Product findById(int productId) {
        Product product = IdentityMap.productMap.get(productId);
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

    public static Pager<Product> getProductPager(int currPage, int pageSize) {
        if(IdentityMap.productMap.isEmpty()){
            String sql = "SELECT PRODUCT_ID id,PRODUCT_NAME productName, STORE_NUM storeNum, "
                    + "PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_STATUS productStatus "
                    + "FROM product";
            List<Product> products = DBHelper.getObjectForList(Product.class, sql);
            for(Product product: products){
                IdentityMap.productMap.put(product.getId(), product);
            }
        }

        PagerHandler pagerHandler = new PagerHandler();
        int dataCount = IdentityMap.productMap.size();
        int pageCount = (dataCount % pageSize == 0) ? (dataCount / pageSize) : (dataCount / pageSize + 1);
        int dataIndex = (currPage - 1) * pageSize;
        List<Product> pageDataList = new ArrayList<>();
        List<Product> allProducts = new ArrayList<>(IdentityMap.productMap.values());
        for (int i = 0; i<pageSize && i< dataCount; i++) {
            Product product = allProducts.get(dataIndex + i);
            pageDataList.add(product);
        }
        Pager<Product> pager = new domain.Pager<Product>(currPage, pageSize, pageCount, dataCount,
                pageDataList);
        return pager;
    }

    @Override
    public int insert(DomainObject obj) {
        return 0;
    }

    @Override
    public void update(DomainObject obj) {
        Product product = (Product)obj;
        String sql = "UPDATE product SET PRODUCT_NAME=?,PRODUCT_PRICE=?,PRODUCT_DESC=?,"
                + "PRODUCT_IMAGE_PATH=?,STORE_NUM=?,PRODUCT_STATUS=? WHERE PRODUCT_ID=?";
        DBHelper.update(sql,product.getProductName(),product.getProductPrice(),product.getProductDesc(),
                product.getProductImagePath(),product.getStoreNum(),product.getProductStatus(),
                product.getId());
        IdentityMap.productMap.put(product.getId(), product);
    }

    @Override
    public void delete(DomainObject obj) {

    }
}
