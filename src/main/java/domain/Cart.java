package domain;

import datasource.ProductMapper;

public class Cart extends DomainObject{
	private int id;
	private int productId;
	private int saleCount;
	private int userId;
	private Product product;

	public Product getProduct() {
		this.product = ProductMapper.findById(this.productId);
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Cart [cardId=" + id + ", productId=" + productId
				+ ", saleCount=" + saleCount + ", userId=" + userId + "]";
	}
	public Cart(int productId, int saleCount, int userId) {
		super();
		this.productId = productId;
		this.saleCount = saleCount;
		this.userId = userId;
	}

	public Cart(int id, int productId, int saleCount, int userId) {
		this.id = id;
		this.productId = productId;
		this.saleCount = saleCount;
		this.userId = userId;
	}

	public Cart() {
		super();
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int cardId) {
		this.id = cardId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
