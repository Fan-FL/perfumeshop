package datasource;

import domain.Cart;
import domain.Order;
import domain.Product;

public class DataMapper {
    public static IMapper getMapper(Class obj){
        if (obj == Order.class){
            return new OrderMapper();
        }else if (obj == CartMapper.class){
            return new CartMapper();
        }if (obj == Product.class){
            return new ProductMapper();
        }if (obj == Cart.class){
            return new CartMapper();
        }else{
            return null;
        }
    }
}
