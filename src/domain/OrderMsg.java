package domain;

import java.util.Date;
import java.util.List;

public class OrderMsg {
	private int orderId;
	private String orderNum;
	private Date orderTime;
	private int orderStatus;
	private String note;
	private int userId;
	private String sendPlace;
	private String sendMan;
	private String sendPhone;
	private List<OrderProduct> product;
	public double getTotalPrice() {
		return totalPrice;
	}

	private double totalPrice;

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
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
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public List<OrderProduct> getProduct() {
		return product;
	}
	public void setProduct(List<OrderProduct> product) {
		this.product = product;
	}
	public OrderMsg(int orderId, String orderNum,
					int orderStatus, String note, int userId, String sendPlace,
					String sendMan, String sendPhone, List<OrderProduct> product
                    ) {
		super();
		this.orderId = orderId;
		this.orderNum = orderNum;
		this.orderStatus = orderStatus;
		this.note = note;
		this.userId = userId;
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
		this.product = product;
	}
	public OrderMsg(int orderId, String orderNum,
					int orderStatus, String note, int userId, String sendPlace,
					String sendMan, String sendPhone) {
		super();
		this.orderId = orderId;
		this.orderNum = orderNum;
		this.orderStatus = orderStatus;
		this.note = note;
		this.userId = userId;
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
	}
	public OrderMsg() {
		super();
	}
	@Override
	public String toString() {
		return "OrderMsg [orderId=" + orderId + ", orderNum=" + orderNum
				+ ", orderTime=" + orderTime + ", orderStatus=" + orderStatus
				+ ", note=" + note + ", userId=" + userId + ", sendPlace="
				+ sendPlace + ", sendMan=" + sendMan + ", sendPhone="
				+ sendPhone + ", product=" + product + "]";
	}

	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}else {
			if(this.getClass() == obj.getClass()){
				OrderMsg o = (OrderMsg) obj;
				if(this.getOrderNum().equals(o.getOrderNum())){
					return true;
				}else{
					return false;
				}

			}else{
				return false;
			}
		}
	}
}
