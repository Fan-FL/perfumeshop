package domain;

import datasource.ProductMapper;

/*
 * ClassName: CartItem
 * Description: The CartItem contains the details about the product, the user and the product.
 */

public class CartItem extends DomainObject {
    private int id;
    private int productId;
    private int saleCount;
    private int userId;
    private Product product;

    public Product getProduct() {
        this.product = new ProductMapper().findById(this.productId);
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

    public CartItem(int productId, int saleCount, int userId) {
        super();
        this.productId = productId;
        this.saleCount = saleCount;
        this.userId = userId;
    }

    public CartItem(int id, int productId, int saleCount, int userId) {
        this.id = id;
        this.productId = productId;
        this.saleCount = saleCount;
        this.userId = userId;
    }

    public CartItem() {
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
