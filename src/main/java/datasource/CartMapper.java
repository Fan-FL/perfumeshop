package datasource;

import domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartMapper implements IMapper{

    /**
     * Get a user's all cart info
     * @param
     * @return
     */
    public static List<CartItem> getAllCartByUser(User user) {
        String sql = "SELECT CART_ID ,PRODUCT_ID ,SALE_COUNT, USER_ID  FROM perfume.cart " +
                "WHERE USER_ID=?;";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        List<CartItem> cartItems = new ArrayList<CartItem>();
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int saleCount = rs.getInt(3);
                int userid = rs.getInt(4);
                CartItem cartItem = IdentityMap.cartItemMap.get(id);
                if(cartItem == null){
                    cartItem = new CartItem(id, productId, saleCount, userid);
                    Product product = new ProductMapper().findById(cartItem.getProductId());
                    cartItem.setProduct(product);
                    IdentityMap.cartItemMap.put(id, cartItem);
                }
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }

        return cartItems;
    }

    public static void deleteCartByUser(User user) {
        String sql = "DELETE FROM perfume.cart WHERE USER_ID=?";
        DBHelper.update(sql, user.getId());
    }

    @Override
    public CartItem findById(int cartId) {
        String sql = "SELECT CART_ID id,PRODUCT_ID productId,SALE_COUNT saleCount,USER_ID userId " +
                "FROM perfume.cart WHERE CART_ID=?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        CartItem cartItem = IdentityMap.cartItemMap.get(cartId);
        if(cartItem != null){
            cartItem = IdentityMap.cartItemMap.get(cartId);
            return cartItem;
        }

        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, cartId);
            rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int saleCount = rs.getInt(3);
                int userid = rs.getInt(4);
                cartItem = new CartItem(id, productId, saleCount, userid);
                Product product = new ProductMapper().findById(cartItem.getProductId());
                cartItem.setProduct(product);
                IdentityMap.cartItemMap.put(id, cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return cartItem;
    }

    @Override
    public int insert(DomainObject obj) {
        CartItem cartItem = (CartItem)obj;
        String sql = "insert into perfume.cart(PRODUCT_ID,SALE_COUNT,USER_ID) values(?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        int generatedKey = -1;

        try {
            ps = DBConnection.prepareReturnKeys(sql);
            ps.setInt(1, cartItem.getProductId());
            ps.setInt(2, cartItem.getSaleCount());
            ps.setInt(3, cartItem.getUserId());
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
        Product product = new ProductMapper().findById(cartItem.getProductId());
        cartItem.setProduct(product);
        cartItem.setId(generatedKey);
        IdentityMap.cartItemMap.put(generatedKey, cartItem);
        return generatedKey;

    }

    @Override
    public void update(DomainObject obj) {
        CartItem cartItem = (CartItem)obj;
        String sql = "UPDATE perfume.cart SET USER_ID=?,PRODUCT_ID=?,SALE_COUNT=? WHERE CART_ID=?";
        DBHelper.update(sql, cartItem.getUserId(), cartItem.getProductId(), cartItem.getSaleCount(),
                cartItem.getId());
        CartItem inMap = IdentityMap.cartItemMap.get(cartItem.getId());
        inMap.setSaleCount(cartItem.getSaleCount());
        inMap.setProduct(cartItem.getProduct());
        inMap.setProductId(cartItem.getProductId());
        inMap.setUserId(cartItem.getUserId());
    }

    @Override
    public void delete(DomainObject obj) {
        CartItem cartItem = (CartItem)obj;
        String sql = "DELETE FROM perfume.cart WHERE CART_ID=?";
        DBHelper.update(sql, cartItem.getId());
        IdentityMap.cartItemMap.remove(cartItem.getId());
    }
}
