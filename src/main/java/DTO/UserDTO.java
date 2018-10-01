package DTO;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int id;
    private String truename;
    private String phone;
    private String address;
    private List<OrderDTO> orders;
    private List<AddressDTO> addresses;
    private List<CartDTO> carts;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<CartDTO> getCarts() {
        return carts;
    }

    public void setCarts(List<CartDTO> carts) {
        this.carts = carts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Element toXmlElement(Branch root) {
        Element userElement = root.addElement("user");
        userElement.addAttribute("id", String.valueOf(id));
        userElement.addAttribute("truename", phone);
        userElement.addAttribute("phone", address);
        Element ordersElement = userElement.addElement("orders");
        for (OrderDTO order : orders) {
            order.toXmlElment(ordersElement);
        }

        Element addressesElement = userElement.addElement("addresses");
        for (AddressDTO address : addresses) {
            address.toXmlElment(addressesElement);
        }

        Element cartsElement = userElement.addElement("carts");
        for (CartDTO cart : carts) {
            cart.toXmlElment(cartsElement);
        }
        return userElement;
    }

    public String toXmlString() {
        Document doc = DocumentHelper.createDocument();
        toXmlElement(doc);
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(stringWriter);
        try {
            xmlWriter.write(doc);
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static UserDTO readXml(Element source) {
        UserDTO result = new UserDTO();

        Element userElement = source.element("user");
        result.setId(Integer.valueOf(userElement.attributeValue("id")));
        result.setTruename(userElement.attributeValue("truename"));
        result.setPhone(userElement.attributeValue("phone"));

        List<OrderDTO> ordersList = new ArrayList<OrderDTO>();
        Element ordersElement = userElement.element("orders");
        List<Element> listOrdersElement = ordersElement.elements();
        for(Element e : listOrdersElement) {
            OrderDTO orderDTO = OrderDTO.readXml(e);
            ordersList.add(orderDTO);
        }
        result.setOrders(ordersList);

        List<AddressDTO> addressesList = new ArrayList<AddressDTO>();
        Element addressesElement = userElement.element("addresses");
        List<Element> listAddressesElement = addressesElement.elements();
        for(Element e : listAddressesElement) {
            AddressDTO addressDTO = AddressDTO.readXml(e);
            addressesList.add(addressDTO);
        }
        result.setAddresses(addressesList);

        List<CartDTO> cartsList = new ArrayList<CartDTO>();
        Element cartsElement = userElement.element("carts");
        List<Element> listCartsElement = cartsElement.elements();
        for(Element e : listCartsElement) {
            CartDTO cartDTO = CartDTO.readXml(e);
            cartsList.add(cartDTO);
        }
        result.setCarts(cartsList);

        return result;
    }

    public static UserDTO readXmlString(Reader input) {
        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(input);
            Element root = doc.getRootElement();
            UserDTO result = readXml(root);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static UserDTO readXmlString(String input) {
        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(input);
            Element root = doc.getRootElement();
            UserDTO result = readXml(root);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}