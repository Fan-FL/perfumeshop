package datasource;

import domain.Address;
import domain.Cart;
import domain.Product;
import domain.User;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {
    public static Map<Integer, Product> productMap = new HashMap<>();
    public static Map<Integer, User> userMap = new HashMap<>();
    public static Map<Integer, Address> addressMap = new HashMap<>();
    public static Map<Integer, Cart> cartMap = new HashMap<>();
}
