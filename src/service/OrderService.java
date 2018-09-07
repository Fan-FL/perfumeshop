package service;

import datasource.*;
import domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        OrderMapper.changeOrderStatus(orderNum,3);
    }

    public String submitOrder(int userId, int addressId, String note, HttpSession session){
        User user = UserMapper.findByID(userId);
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
            // total price of order
            double totalPrice = 0;
            // 0 represent order created
            int orderStatus = 0;
            List<Order> orders = new ArrayList<Order>();
            for(Cart cart :user.getCartItems()){
                // Get product by product id for checking inventory and product status
                Product product = ProductMapper.findById(cart.getProduct().getId());
                int saleCount = cart.getSaleCount();
                int storeNum = product.getStoreNum();
                int productStatus = product.getProductStatus();
                product.setStoreNum(storeNum - saleCount);
                UnitOfWork.getCurrent().registerDirty(product);
                if(productStatus==0){
                    UnitOfWork.getCurrent().rollBack();
                    return "productOffShelf.jsp";
                }
                if(saleCount > storeNum){
                    UnitOfWork.getCurrent().rollBack();
                    return "outOfStockNum.jsp";
                }
                Order order = new Order(orderNum, orderTime, orderStatus, note, userId,
                        address.getSendPlace(),address.getSendMan(), address.getSendPhone(),
                        product.getId(), product.getProductName(),
                        product.getProductPrice(), cart.getSaleCount());
                orders.add(order);
                totalPrice += product.getProductPrice()*cart.getSaleCount();
            }
            //create orders
            int i = 1;
            for(Order order:orders){
                order.setId(i++);
                UnitOfWork.getCurrent().registerNew(order);
            }
            // delete products in cart
            for (Cart cart: user.getCartItems()){
                UnitOfWork.getCurrent().registerDeleted(cart);
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
        User user = UserMapper.findByID(userId);
        List<OrderMsg> userOrderMsgs = user.getOrderMsgs();
        return userOrderMsgs;
    }

    public void addOrder(int userId, HttpServletRequest request, HttpServletResponse
            response)  throws ServletException, IOException {
        User user = UserMapper.findByID(userId);
        List<Address> addresses = user.getDeliveryAddresses();
        request.setAttribute("addresses", addresses);
        if(user.getCartItems().isEmpty()){
            request.getRequestDispatcher("viewcart").forward(request, response);
        }else{
            Map<Cart, Product> cartProductMap = new HashMap<>();
            if (!user.getCartItems().isEmpty()){
                for (Cart cart: user.getCartItems()){
                    cartProductMap.put(cart, cart.getProduct());
                }
            }
            request.getSession().setAttribute("cartProductMap", cartProductMap);
            request.getRequestDispatcher("createOrder.jsp").forward(request, response);
        }
    }
}