package domain;

import datasource.CartMapper;

import java.util.List;

/*
 * ClassName: Cart
 * Description: The Cart class has a list of items selected by the user for shopping.
 */

public class Cart extends DomainObject{
    List<CartItem> cartItems;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /*
     * Parameters: cartItem
     * Return: none
     * Description: add item to the cart
     * */
    public void addCartItem(CartItem cartItem) {
        this.getCartItems().add(cartItem);
        new CartMapper().insert(cartItem);
    }

    /*
     * Parameters: cartItem
     * Return: none
     * Description: delete item from the cart
     * */
    public void deleteCartItem(CartItem cartItem) {
        new CartMapper().delete(cartItem);
        this.getCartItems().remove(cartItem);
    }

    /*
     * Parameters: cartId, saleCount
     * Return: none
     * Description: update the items in the cart
     * */
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

    /*
     * Parameters: user
     * Return: none
     * Description: delete the cart
     * */
    public void clear(User user) {
        this.cartItems.clear();
        CartMapper.deleteCartByUser(user);
    }
}
