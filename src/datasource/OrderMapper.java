package datasource;

import domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class OrderMapper implements IMapper{

    public static int getOrderStatus(String orderNum) {
        int orderStatus = -1;
        String sql = "SELECT DISTINCT ORDER_STATUS FROM perfume.orders WHERE ORDER_NUM=?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            if (rs.next()){
                orderStatus = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return orderStatus;
    }

    /**
     * //modify order status to 1 which means already paid
     * @param orderNum
     */
    public static void submitPayment(String orderNum) {
        changeOrderStatus(orderNum,1);
    }

    public static void changeOrderStatus(String orderNum, int status) {
        String sql = "UPDATE perfume.orders SET ORDER_STATUS=? WHERE ORDER_NUM=?";
        DBHelper.update(sql, status, orderNum);
    }

    public static List<OrderMsg> getOrderMsgs(int userId) {
        // get all undeleted order number
        Set<String> orderNums = getOrderNum(userId);
        Iterator<String> it = orderNums.iterator();
        List<OrderMsg> ordermsgs = new ArrayList<OrderMsg>();
        while(it.hasNext()){
            String orderNum = it.next();
            //get all OrderProducts by orderNum
            List<OrderProduct> product = getOrderProducts(orderNum);
            //get order message
            OrderMsg order = getOrderMsg(orderNum,product);
            ordermsgs.add(order);
        }
        return ordermsgs;
    }

    /**
     * get all undeleted order number by userID in order of date (The most recent one appears on
     * the top)
     *
     * @param userId
     * @return
     */
    public static Set<String> getOrderNum(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            Set<String> orderNums = new TreeSet<String>(new Comparator<String>(){
                public int compare(String s1,String s2){
                    return s2.compareTo(s1);
                }
            });
            conn = DBConnection.getDBConnection();
            String sql = "SELECT ORDER_NUM FROM perfume.orders WHERE VISIBLE=1 AND USER_ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while(rs.next()){
                orderNums.add(rs.getString("ORDER_NUM"));
            }
            return orderNums;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return null;
    }

    /**
     * get all OrderProducts by orderNum
     * @param orderNum
     * @return
     */
    public static List<OrderProduct> getOrderProducts(String orderNum) {
        String sql = "SELECT orders.PRODUCT_ID ,orders.PRODUCT_NAME ,orders.PRODUCT_PRICE ,orders.SALE_COUNT ," +
                "product.PRODUCT_IMAGE_PATH  FROM perfume.orders,perfume.product WHERE orders.PRODUCT_ID=product.PRODUCT_ID AND ORDER_NUM=?;";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        List<OrderProduct> orderProducts = new ArrayList<>();
        try {
            ps = DBConnection.prepare(sql);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String productName = rs.getString(2);
                double productPrice = rs.getDouble(3);
                int saleCount = rs.getInt(4);
                String productImagePath = rs.getString(5);
                OrderProduct orderProduct = new OrderProduct(id, productName, productPrice,
                    productImagePath, saleCount);
                orderProducts.add(orderProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return orderProducts;
    }

    /**
     * get order message by order num and OrderProducts
     * @param orderNum
     * @param product
     * @return
     */
    public static OrderMsg getOrderMsg(String orderNum, List<OrderProduct> product) {
        OrderMsg orderMsg = null;
        String sql = "SELECT ORDER_ID , ORDER_NUM, " +
                " ORDER_STATUS ,NOTE ,USER_ID , " +
                "SEND_PLACE ,SEND_MAN ,SEND_PHONE  FROM perfume.orders WHERE ORDER_NUM=?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setString(1, orderNum);
            rs = ps.executeQuery();
            while (rs.next()){
                int orderId = rs.getInt(1);
                orderNum = rs.getString(2);
                int orderStatus = rs.getInt(3);
                String note = rs.getString(4);
                int userId = rs.getInt(5);
                String sendPlace = rs.getString(6);
                String sendMan = rs.getString(7);
                String sendPhone = rs.getString(8);
                orderMsg = new OrderMsg(orderId, orderNum, orderStatus, note,
                        userId, sendPlace, sendMan, sendPhone);
                orderMsg.setProduct(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return orderMsg;
    }

    public static void changeOrderVisible(String orderNum, int status) {
        String sql = "UPDATE perfume.orders SET VISIBLE=? WHERE ORDER_NUM=?";
        DBHelper.update(sql, status, orderNum);
    }

    @Override
    public int insert(DomainObject obj) {
        Order order = (Order)obj;
        String sql = "INSERT INTO perfume.orders (ORDER_NUM,ORDER_STATUS,NOTE,USER_ID,SEND_PLACE,"
                + "SEND_MAN,SEND_PHONE,PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,SALE_COUNT)"
                + "values(?,?,?,?,?,?,?,?,?,?,?)";
        return DBHelper.updateGetGeneratedKeys(sql, order.getOrderNum(),  order.getOrderStatus(),
                order.getNote(), order.getUserId(), order.getSendPlace(), order.getSendMan(), order.getSendPhone(),
                order.getProductId(), order.getProductName(), order.getProductPrice(), order.getSaleCount());
    }

    @Override
    public void update(DomainObject obj) {

    }

    @Override
    public void delete(DomainObject obj) {

    }
}
