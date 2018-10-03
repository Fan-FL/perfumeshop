package datasource;

import domain.Address;
import domain.CartItem;
import domain.Product;
import domain.User;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {
    public static Map<Integer, Product> productMap = new HashMap<Integer, Product>();
    public static Map<Integer, User> userMap = new HashMap<Integer, User>();
    public static Map<Integer, Address> addressMap = new HashMap<Integer, Address>();
    public static Map<Integer, CartItem> cartItemMap = new HashMap<Integer, CartItem>();
}
