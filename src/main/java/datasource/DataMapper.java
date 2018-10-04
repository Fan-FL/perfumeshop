package datasource;

import domain.CartItem;
import domain.OrderItem;
import domain.Product;

public class DataMapper {
    public static IMapper getMapper(Class obj){
        if (obj == OrderItem.class){
            return new OrderMapper();
        }else if (obj == CartMapper.class){
            return new CartMapper();
        }if (obj == Product.class){
            return new ProductMapper();
        }if (obj == CartItem.class){
            return new CartMapper();
        }else{
            return null;
        }
    }
}
