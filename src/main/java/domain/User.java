package domain;

import datasource.AddressMapper;
import datasource.CartMapper;
import datasource.OrderMapper;
import datasource.UserMapper;
import java.util.List;

/*
 * ClassName:User
 * Description:The User class contains all the details about the registered customers
 * 			   and functions they can operate.
 */

public class User extends Account {

	private int id;
	private String truename;
	private String phone;
	private String address;
	private int userStatus = -1;
	private List<OrderMsg> orderMsgs;
	private List<Address> deliveryAddresses;
	private Cart cart = new Cart();

	/*
	 * Parameters: None
	 * Return: cart
	 * Description: User can get his or her cart
	 * */
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/*
	 * Parameters: None
	 * Return: CartItem list
	 * Description: User can get cart items through the CartMapper
	 * */
	public List<CartItem> getCartItems() {
		if(this.cart.getCartItems() == null){
			this.cart.setCartItems(CartMapper.getAllCartByUser(this));
		}
		return this.cart.getCartItems();
	}

	/*
	 * Parameters: None
	 * Return: OrderMsg list
	 * Description: User can get his or her order message through the OrderMapper
	 * */
	public List<OrderMsg> getOrderMsgs() {
		this.orderMsgs = OrderMapper.getOrderMsgs(this.id);
		return this.orderMsgs;
	}

	public void setOrderMsgs(List<OrderMsg> orderMsgs) {
		this.orderMsgs = orderMsgs;
	}

	/*
	 * Parameters: None
	 * Return: the deliveryAddresses
	 * Description:One user can have many delivery addresses.
	 * 			   The address can be found by user id through the AddressMapper
	 * */
	public List<Address> getDeliveryAddresses() {
		if(this.deliveryAddresses == null){
			this.deliveryAddresses = AddressMapper.getAddressByUserId(this.id);
		}
		return this.deliveryAddresses;
	}

	public void setDeliveryAddresses(List<Address> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}

	/*
	 * Parameters: deliveryAddress
	 * Return: none
	 * Description: Remove the delivery address from the Address list.
	 * */
	public void deleteDeliveryAddress(Address deliveryAddress){
		if(this.deliveryAddresses == null){
			this.deliveryAddresses = AddressMapper.getAddressByUserId(this.id);
			this.deliveryAddresses.remove(deliveryAddress);
		}else {
			this.deliveryAddresses.remove(deliveryAddress);
		}
		new AddressMapper().delete(deliveryAddress);
	}

	public User(int userId, String username, String password, String truename,
				String phone, String address, int userStatus) {
		super();
		this.id = userId;
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
		this.userStatus = userStatus;
		this.type = "user";
	}

	public User() {
		super();
		this.type = "user";
	}

	public User(int userId, String username, String password) {
		super();
		this.id = userId;
		this.username = username;
		this.password = password;
		this.type = "user";
	}

	@Override
	public void setId(int userId) {
		this.id = userId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getUsername() {
		if (this.username == null){
			load();
		}
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		if (this.password == null){
			load();
		}
		return password;
	}
	@Override
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
		return "User [userId=" + id + ", username=" + username + ", password=" + password + ", truename=" + truename
				+ ", phone=" + phone + ", address=" + address + ", userStatus=" + userStatus + "]";
	}

	public void load(){
		if(this.username == null){
			this.username = UserMapper.getUserName(this.id);
		}
		if(this.address == null){
			this.username = UserMapper.getUserName(this.id);
		}
		if(this.phone == null){
			this.username = UserMapper.getPhone(this.id);
		}
		if(this.truename == null){
			this.username = UserMapper.getTrueName(this.id);
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

	/*
	 * Parameters: addressId
	 * Return: address // null
	 * Description: use the addressID to get the delivery address.
	 * 				If it can be found in the Address list,return address;
	 * 				otherwise return null
	 * */
	public Address getDeliveryAddress(int addressId){
		if(!this.getDeliveryAddresses().isEmpty())
		for (Address address: this.getDeliveryAddresses()){
			if (address.getId() == addressId){
				return address;
			}
		}
		return null;
	}

	public void deleteCart() {
		this.getCart().clear(this);
	}
}
