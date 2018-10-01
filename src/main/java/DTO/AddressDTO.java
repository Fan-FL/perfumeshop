package DTO;

import org.dom4j.Element;

public class AddressDTO {
    private String sendPlace;

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

    private String sendMan;
    private String sendPhone;
    public void toXmlElment(Element root) {
        Element address = root.addElement("address");
        address.addAttribute("sendPlace", sendPlace);
        address.addAttribute("sendMan",sendMan);
        address.addAttribute("sendPhone", sendPhone);
    }

    public static AddressDTO readXml(Element source) {
        AddressDTO result = new AddressDTO();
        result.setSendPlace(source.attributeValue("sendPlace"));
        result.setSendMan(source.attributeValue("sendMan"));
        result.setSendPhone(source.attributeValue("sendPhone"));
        return result;
    }
}
