package domain;

import datasource.AddressMapper;
import datasource.CartMapper;
import datasource.OrderMapper;
import datasource.UserMapper;

import java.util.List;

public class User extends DomainObject{

	private int userId;
	private String username;
	private String password;
	private String truename;
	private String phone;
	private String address;
	private int userStatus = -1;
	private List<OrderMsg> orderMsgs;
	private List<Address> deliveryAddresses;
	private List<Cart> cartItems;

	public List<OrderMsg> getOrderMsgs() {
		this.orderMsgs = OrderMapper.getOrderMsgs(this.userId);
		return this.orderMsgs;
	}

	public void setOrderMsgs(List<OrderMsg> orderMsgs) {
		this.orderMsgs = orderMsgs;
	}

	public List<Address> getDeliveryAddresses() {
		if(this.deliveryAddresses == null){
			this.deliveryAddresses = AddressMapper.getAddressByUserId(this.userId);
		}
		return this.deliveryAddresses;
	}

	public void setDeliveryAddresses(List<Address> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}

	public void deleteDeliveryAddress(Address deliveryAddress){
		if(this.deliveryAddresses == null){
			this.deliveryAddresses = AddressMapper.getAddressByUserId(this.userId);
			this.deliveryAddresses.remove(deliveryAddress);
		}else {
			this.deliveryAddresses.remove(deliveryAddress);
		}
		new AddressMapper().delete(deliveryAddress);
	}

	public List<Cart> getCartItems() {
		if(cartItems == null){
			this.cartItems = CartMapper.getAllCartByUser(this);
		}
		return cartItems;
	}

	public void setCartItems(List<Cart> cartItems) {
		this.cartItems = cartItems;
	}

	public User(int userId, String username, String password, String truename,
                String phone, String address, int userStatus) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
		this.userStatus = userStatus;
	}

	public User() {
		super();
	}

	public User(int userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	@Override
	public void setId(int userId) {
		this.userId = userId;
	}

	@Override
	public int getId() {
		return userId;
	}

	public String getUsername() {
		if (this.username == null){
			load();
		}
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		if (this.password == null){
			load();
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getTruename() {
		if (this.truename == null){
			load();
		}
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getPhone() {
		if (this.phone == null){
			load();
		}
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		if (this.address == null){
			load();
		}
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public int getUserStatus() {
		if (this.userStatus == -1){
			load();
		}
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", truename=" + truename
				+ ", phone=" + phone + ", address=" + address + ", userStatus=" + userStatus + "]";
	}

	public void load(){
		User user = UserMapper.findByID(this.userId);
		if(this.username == null){
			this.username = user.username;
		}
		if(this.address == null){
			this.address = user.address;
		}
		if(this.password == null){
			this.password = user.password;
		}
		if(this.phone == null){
			this.phone = user.phone;
		}
		if(this.truename == null){
			this.truename = user.truename;
		}
		if(this.userStatus == -1){
			this.userStatus = user.userStatus;
		}
	}

	public void updateDeliveryAddress(Address address) {
		new AddressMapper().update(address);
		this.getDeliveryAddresses().remove(address);
		this.getDeliveryAddresses().add(address);
	}

	public void addDeliveryAddress(Address address) {
		new AddressMapper().insert(address);
		this.getDeliveryAddresses().add(address);
	}

	public Address getDeliveryAddress(int addressId){
		if(!this.getDeliveryAddresses().isEmpty())
		for (Address address: this.getDeliveryAddresses()){
			if (address.getId() == addressId){
				return address;
			}
		}
		return null;
	}

	public void addCart(Cart cart) {
		this.getCartItems().add(cart);
		new CartMapper().insert(cart);
	}

	public void deleteAllCart() {
		CartMapper.deleteCartByUser(this);
		this.getCartItems().clear();
	}

	public void deleteCart(Cart cart) {
		new CartMapper().delete(cart);
		this.getCartItems().remove(cart);
	}

	public void updateCartCount(int cartId, int saleCount) {
		CartMapper cartMapper = new CartMapper();
		for(Cart cart: this.getCartItems()){
			if(cart.getId() == cartId){
				cart.setSaleCount(saleCount);
				cartMapper.update(cart);
				return;
			}
		}
	}
}
