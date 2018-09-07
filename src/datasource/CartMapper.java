package datasource;

import domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartMapper implements IMapper{


    /**
     * Get a user's all cart info
     * @param
     * @return
     */
    public static List<Cart> getAllCartByUser(User user) {
        String sqlCart = "SELECT CART_ID id,PRODUCT_ID productId,SALE_COUNT saleCount FROM cart " +
                "WHERE USER_ID=?;";
        List<Cart> cartList = DBHelper.getObjectForList(Cart.class, sqlCart, user.getId());
//        for (Cart cart: cartList){
//            Product product = ProductMapper.findById(cart.getProductId());
//            cart.setProduct(product);
//        }

        return cartList;
    }

    public static void deleteCartByUser(User user) {
        String sql = "DELETE FROM cart WHERE USER_ID=?";
        DBHelper.update(sql, user.getId());
    }

    public static Cart getCart(int cartId) {
        String sql = "SELECT CART_ID id,PRODUCT_ID productId,SALE_COUNT saleCount,USER_ID userId " +
                "FROM cart WHERE CART_ID=?";
        return DBHelper.getObject(Cart.class, sql, cartId);
    }

    /**
     * get cart info of certain user and carDis
     * @param userId
     * @param cartIds
     * @return
     */
    public static Map<Cart, Product> getCartProductMap(int userId, String[] cartIds) {
        String strCartIds = "";
        for (int i = 0; i < cartIds.length - 1; i++) {
            strCartIds += "?,";
        }
        String sqlCart = "SELECT CART_ID id,PRODUCT_ID productId,SALE_COUNT saleCount "
                + "FROM cart WHERE CART_ID IN (" + strCartIds + "?) AND USER_ID=?";
        String sqlProduct = "SELECT PRODUCT_STATUS productStatus,PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE "
                + "productPrice,PRODUCT_IMAGE_PATH productImagePath,STORE_NUM storeNum FROM "
                + "product WHERE PRODUCT_ID=?";
        Object[] sqlArray = new Object[cartIds.length + 1];
        for (int i = 0; i < cartIds.length; i++) {
            sqlArray[i] = cartIds[i];
        }
        sqlArray[sqlArray.length - 1] = userId;
        return DBHelper.getMapHandler(sqlCart, Cart.class, sqlProduct, Product.class, "PRODUCT_ID", sqlArray);
    }

    public static List<Cart> getCartsByOrderNum(String orderNum) {
        String sql = "SELECT PRODUCT_ID productId,SALE_COUNT saleCount FROM orders WHERE ORDER_NUM=?";
        return DBHelper.getObjectForList(Cart.class, sql, orderNum);
    }

    @Override
    public int insert(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "insert into cart(PRODUCT_ID,SALE_COUNT,USER_ID) values(?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        int generatedKey = -1;

        try {
            ps = DBConnection.prepareReturnKeys(sql);
            ps.setInt(1, cart.getProductId());
            ps.setInt(2, cart.getSaleCount());
            ps.setInt(3, cart.getUserId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        Product product = ProductMapper.findById(cart.getProductId());
        cart.setProduct(product);
        cart.setId(generatedKey);
        return generatedKey;

    }

    @Override
    public void update(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "UPDATE cart SET USER_ID=?,PRODUCT_ID=?,SALE_COUNT=? WHERE CART_ID=?";
        DBHelper.update(sql, cart.getUserId(), cart.getProductId(), cart.getSaleCount(),
                cart.getId());
    }

    @Override
    public void delete(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "DELETE FROM cart WHERE CART_ID=?";
        DBHelper.update(sql, cart.getId());
    }
}
