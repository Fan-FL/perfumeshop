package domain;

import datasource.CartMapper;

import java.util.List;

public class Cart extends DomainObject{
    List<CartItem> cartItems;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


    public void addCart(CartItem cartItem) {
        this.getCartItems().add(cartItem);
        new CartMapper().insert(cartItem);
    }

    public void deleteCartItem(CartItem cartItem) {
        new CartMapper().delete(cartItem);
        this.getCartItems().remove(cartItem);
    }

    public void updateCartCount(int cartId, int saleCount) {
        CartMapper cartMapper = new CartMapper();
        for(CartItem cartItem : this.getCartItems()){
            if(cartItem.getId() == cartId){
                cartItem.setSaleCount(saleCount);
                cartMapper.update(cartItem);
                return;
            }
        }
    }

    public void clear(User user) {
        this.cartItems.clear();
        CartMapper.deleteCartByUser(user);
    }
}
