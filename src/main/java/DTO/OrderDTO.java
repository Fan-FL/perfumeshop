package DTO;

import org.dom4j.Element;

import java.util.Date;

public class OrderDTO {
    private String orderNum;
    private Date orderTime;
    private String sendPlace;
    private String sendMan;
    private String sendPhone;
    private int productId;
    private String productName;
    private double productPrice;
    private int saleCount;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getSendPlace() {
        return sendPlace;
    }

    public void setSendPlace(String sendPlace) {
        this.sendPlace = sendPlace;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public void toXmlElment(Element root) {
        Element order = root.addElement("order");
        order.addAttribute("orderNum", orderNum);
        order.addAttribute("orderTime", orderTime.toString());
        order.addAttribute("sendPlace", sendPlace);
        order.addAttribute("sendMan", sendMan);
        order.addAttribute("sendPhone", sendPhone);
        order.addAttribute("productId", String.valueOf(productId));
        order.addAttribute("productName", productName);
        order.addAttribute("productPrice", String.valueOf(productPrice));
        order.addAttribute("saleCount", String.valueOf(saleCount));
    }

    public static OrderDTO readXml(Element source) {
        OrderDTO result = new OrderDTO();
        result.setOrderNum(source.attributeValue("orderNum"));
        result.setOrderTime(new Date(source.attributeValue("orderTime")));
        result.setSendPlace(source.attributeValue("sendPlace"));
        result.setSendMan(source.attributeValue("sendMan"));
        result.setSendPhone(source.attributeValue("sendPhone"));
        result.setProductId(Integer.valueOf(source.attributeValue("productId")));
        result.setProductName(source.attributeValue("productName"));
        result.setProductPrice(Integer.valueOf(source.attributeValue("productPrice")));
        result.setSaleCount(Integer.valueOf(source.attributeValue("saleCount")));
        return result;
    }
}
