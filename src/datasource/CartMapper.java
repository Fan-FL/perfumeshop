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
    public static List<Cart> getAllCartByUser(User user) {
        String sql = "SELECT CART_ID ,PRODUCT_ID ,SALE_COUNT, USER_ID  FROM perfume.cart " +
                "WHERE USER_ID=?;";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        List<Cart> carts = new ArrayList<>();
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int saleCount = rs.getInt(3);
                int userid = rs.getInt(4);
                Cart cart = IdentityMap.cartMap.get(id);
                if(cart == null){
                    cart = new Cart(id, productId, saleCount, userid);
                    Product product = ProductMapper.findById(cart.getProductId());
                    cart.setProduct(product);
                    IdentityMap.cartMap.put(id, cart);
                }
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }

        return carts;
    }

    public static void deleteCartByUser(User user) {
        String sql = "DELETE FROM perfume.cart WHERE USER_ID=?";
        DBHelper.update(sql, user.getId());
    }

    public static Cart getCart(int cartId) {
        String sql = "SELECT CART_ID id,PRODUCT_ID productId,SALE_COUNT saleCount,USER_ID userId " +
                "FROM perfume.cart WHERE CART_ID=?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        Cart cart = IdentityMap.cartMap.get(cartId);
        if(cart != null){
            cart = IdentityMap.cartMap.get(cartId);
            return cart;
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
                cart = new Cart(id, productId, saleCount, userid);
                Product product = ProductMapper.findById(cart.getProductId());
                cart.setProduct(product);
                IdentityMap.cartMap.put(id, cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return cart;
    }

    @Override
    public int insert(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "insert into perfume.cart(PRODUCT_ID,SALE_COUNT,USER_ID) values(?, ?, ?)";
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
        IdentityMap.cartMap.put(generatedKey, cart);
        return generatedKey;

    }

    @Override
    public void update(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "UPDATE perfume.cart SET USER_ID=?,PRODUCT_ID=?,SALE_COUNT=? WHERE CART_ID=?";
        DBHelper.update(sql, cart.getUserId(), cart.getProductId(), cart.getSaleCount(),
                cart.getId());
        Cart inMap = IdentityMap.cartMap.get(cart.getId());
        inMap.setSaleCount(cart.getSaleCount());
        inMap.setProduct(cart.getProduct());
        inMap.setProductId(cart.getProductId());
        inMap.setUserId(cart.getUserId());
    }

    @Override
    public void delete(DomainObject obj) {
        Cart cart = (Cart)obj;
        String sql = "DELETE FROM perfume.cart WHERE CART_ID=?";
        DBHelper.update(sql, cart.getId());
        IdentityMap.cartMap.remove(cart.getId());
    }
}
