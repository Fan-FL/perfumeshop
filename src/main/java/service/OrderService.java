package service;

import datasource.*;
import domain.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

public class OrderService {
    private static OrderService instance;
    static {
        instance = new OrderService();
    }
    public static OrderService getInstance() {
        return instance;
    }

    private OrderService(){}

    public void deleteOrder(String orderNum){
        OrderMapper.changeOrderVisible(orderNum, 0);
    }

    public int getOrderStatus(String orderNum){
        return OrderMapper.getOrderStatus(orderNum);
    }

    public void submitPayment(String orderNum){
        OrderMapper.submitPayment(orderNum);
    }

    public void receiveProduct(String orderNum){
        OrderMapper.changeOrderStatus(orderNum,2);
    }

    public String submitOrder(int userId, int addressId, String note, HttpSession session){
        User user = new UserMapper().findById(userId);
        String orderNum = null;
        UnitOfWork.newCurrent();
        try {
            Address address = user.getDeliveryAddress(addressId);
            Timestamp orderTime = new Timestamp(System.currentTimeMillis());
            Random random = new Random();
            String ran = random.nextInt(10)*1000+random.nextInt(10)*100+random.nextInt(10)*10+random.nextInt(10) + "";
            System.out.println(ran);
            orderNum = System.currentTimeMillis() + ran + user.getId();
            System.out.println(orderNum);
            // total price of Order
            double totalPrice = 0;
            // 0 represent Order created
            int orderStatus = 0;
            List<OrderItem> orderItems = new ArrayList<OrderItem>();
            for(CartItem cartItem :user.getCartItems()){
                // Get product by product id for checking inventory and product status
                Product product = new ProductMapper().findById(cartItem.getProduct().getId());
                UnitOfWork.getCurrent().registerDirty(product);
                int saleCount = cartItem.getSaleCount();
                int storeNum = product.getStoreNum();
                int productStatus = product.getProductStatus();
                product.setStoreNum(storeNum - saleCount);
                if(productStatus==0){
                    UnitOfWork.getCurrent().rollBack();
                    return "productOffShelf.jsp";
                }
                if(saleCount > storeNum){
                    UnitOfWork.getCurrent().rollBack();
                    return "outOfStockNum.jsp";
                }
                OrderItem orderItem = new OrderItem(orderNum, orderTime, orderStatus, note, userId,
                        address.getSendPlace(),address.getSendMan(), address.getSendPhone(),
                        product.getId(), product.getProductName(),
                        product.getProductPrice(), cartItem.getSaleCount());
                orderItems.add(orderItem);
                totalPrice += product.getProductPrice()* cartItem.getSaleCount();
            }
            //create orders
            int i = 1;
            for(OrderItem orderItem : orderItems){
                orderItem.setId(i++);
                UnitOfWork.getCurrent().registerNew(orderItem);
            }
            // delete products in cart
            for (CartItem cartItem : user.getCartItems()){
                UnitOfWork.getCurrent().registerDeleted(cartItem);
            }
            UnitOfWork.getCurrent().commit();
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("orderNum", orderNum);
            session.setAttribute("address", address);
        } catch (Exception e) {
            e.printStackTrace();
            UnitOfWork.getCurrent().rollBack();
            return "order_create_fail.jsp";
        }
        user.getCartItems().clear();
        user.getOrderMsgs();
        return "order_create_success.jsp";
    }

    public List<OrderMsg> viewMyOrder(int userId){
        User user = new UserMapper().findById(userId);
        List<OrderMsg> userOrderMsgs = user.getOrderMsgs();
        return userOrderMsgs;
    }
}
