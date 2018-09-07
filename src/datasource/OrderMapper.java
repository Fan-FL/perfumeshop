package datasource;

import domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class OrderMapper implements IMapper{

    public static int getOrderStatus(String orderNum) {
        String sql = "SELECT DISTINCT ORDER_STATUS FROM orders WHERE ORDER_NUM=?";
        return Integer.parseInt(DBHelper.getValue(sql, orderNum).toString());
    }

    /**
     * //modify order status to 1 which means already paid
     * @param orderNum
     */
    public static void submitPayment(String orderNum) {
        changeOrderStatus(orderNum,1);
    }

    public static void changeOrderStatus(String orderNum, int status) {
        String sql = "UPDATE orders SET ORDER_STATUS=? WHERE ORDER_NUM=?";
        DBHelper.update(sql, status, orderNum);
    }

    public static List<OrderMsg> getOrderMsgs(int userId) {
        // get all undeleted order number
        Set<String> orderNums = getOrderNum(userId);
        Iterator<String> it = orderNums.iterator();
        List<OrderMsg> ordermsg = new ArrayList<OrderMsg>();
        while(it.hasNext()){
            String orderNum = it.next();
            //get all OrderProducts by orderNum
            List<OrderProduct> product = getOrderProducts(orderNum);
            //get order message
            OrderMsg order = getOrderMsg(orderNum,product);
            ordermsg.add(order);
        }
        return ordermsg;
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
            String sql = "SELECT ORDER_NUM FROM orders WHERE VISIBLE=1 AND USER_ID=?";
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
        String sql = "SELECT orders.PRODUCT_ID productId,orders.PRODUCT_NAME productName,orders.PRODUCT_PRICE productPrice,orders.SALE_COUNT saleCount,product.PRODUCT_IMAGE_PATH productImagePath FROM orders,product WHERE orders.PRODUCT_ID=product.PRODUCT_ID AND ORDER_NUM=?;";
        List<OrderProduct> product = DBHelper.getObjectForList(OrderProduct.class, sql, orderNum);
        return product;
    }

    /**
     * get order message by order num and OrderProducts
     * @param orderNum
     * @param product
     * @return
     */
    public static OrderMsg getOrderMsg(String orderNum, List<OrderProduct> product) {
        String sql = "SELECT ORDER_ID id, ORDER_NUM orderNum,ORDER_TIME " +
                "orderTime,ORDER_STATUS orderStatus,NOTE note,USER_ID userId, " +
                "SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE sendPhone FROM orders WHERE ORDER_NUM=?";
        OrderMsg ordermsg = DBHelper.getObject(OrderMsg.class, sql, orderNum);
        ordermsg.setProduct(product);
        return ordermsg;
    }

    public static void changeOrderVisible(String orderNum, int status) {
        String sql = "UPDATE orders SET VISIBLE=? WHERE ORDER_NUM=?";
        DBHelper.update(sql, status, orderNum);
    }

    @Override
    public int insert(DomainObject obj) {
        Order order = (Order)obj;
        String sql = "INSERT INTO orders (ORDER_NUM,ORDER_TIME,ORDER_STATUS,NOTE,USER_ID,SEND_PLACE,"
                + "SEND_MAN,SEND_PHONE,PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,SALE_COUNT)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
        return DBHelper.updateGetGeneratedKeys(sql, order.getOrderNum(), order.getOrderTime(), order
                        .getOrderStatus(),
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
