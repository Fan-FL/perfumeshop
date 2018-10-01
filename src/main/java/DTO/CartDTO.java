package DTO;

import org.dom4j.Element;

public class CartDTO {
    private int productId;
    private int saleCount;

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

    public void toXmlElment(Element root) {
        Element address = root.addElement("address");
        address.addAttribute("productId", String.valueOf(productId));
        address.addAttribute("saleCount", String.valueOf(saleCount));
    }

    public static CartDTO readXml(Element source) {
        CartDTO result = new CartDTO();
        result.setProductId(Integer.valueOf(source.attributeValue("productId")));
        result.setSaleCount(Integer.valueOf(source.attributeValue("saleCount")));
        return result;
    }
}
