package service;

import datasource.UserMapper;
import domain.CartItem;
import domain.Product;
import domain.User;

import java.util.HashMap;
import java.util.Map;

public class CartService {
    private static CartService instance;
    static {
        instance = new CartService();
    }
    public static CartService getInstance() {
        return instance;
    }

    private CartService(){}

    public int addToCart(int productId, int userId, int saleCount){
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);
        cartItem.setSaleCount(saleCount);
        User user = new UserMapper().findById(userId);
        user.getCart().addCartItem(cartItem);
        return cartItem.getId();
    }

    public int getCartCount(int userId){
        User user = new UserMapper().findById(userId);
        return user.getCartItems().size();
    }

    public void deleteCartByUser(int userId){
        User user = new UserMapper().findById(userId);
        user.deleteCart();
    }

    public void deleteCartItemById(int userId, int cartId){
        User user = new UserMapper().findById(userId);
        CartItem cartItem = new CartItem();
        cartItem.setId(cartId);
        user.getCart().deleteCartItem(cartItem);
    }

    public void updateCartCount(int userId, int cartId, int saleCount){
        User user = new UserMapper().findById(userId);
        user.getCart().updateCartCount(cartId, saleCount);
    }

    public Map<CartItem, Product> GetAllCartInfoByUserID(int userId){
        User user = new UserMapper().findById(userId);
        Map<CartItem, Product> map = new HashMap<CartItem, Product>();
        if (!user.getCartItems().isEmpty()){
            for (CartItem cartItem : user.getCartItems()){
                map.put(cartItem, cartItem.getProduct());
            }
        }
        return map;
    }
}
