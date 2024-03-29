package domain;

/*
 * ClassName: Product
 * Description: The Product class contains all the details about the available product(perfumes)
 */
public class Product extends DomainObject {
    private int id;
    private String productName;
    private double productPrice;
    private String productDesc;
    private String productImagePath;
    private int storeNum;
    private int productStatus;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int productId) {
        this.id = productId;
    }

    public String getProductName() {
        return productName == null ? "" : productName;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "Product [productId=" + id + ", productName=" + productName + ", productPrice=" + productPrice
                + ", productDesc=" + productDesc + ", productImagePath=" + productImagePath + ", storeNum=" + storeNum
                + ", productStatus=" + productStatus + "]";
    }

    public Product(int productId, String productName, double productPrice, String productDesc, String productImagePath,
                   int storeNum, int productStatus) {
        super();
        this.id = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productImagePath = productImagePath;
        this.storeNum = storeNum;
        this.productStatus = productStatus;
    }

    public Product(String productName, double productPrice, String productDesc, String productImagePath, int storeNum,
                   int productStatus) {
        super();
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productImagePath = productImagePath;
        this.storeNum = storeNum;
        this.productStatus = productStatus;
    }

    public Product() {
        super();
    }


}
