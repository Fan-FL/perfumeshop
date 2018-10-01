package service;

import datasource.UserMapper;
import domain.Address;
import domain.User;

import java.util.List;

public class AddressService {

    private static AddressService instance;
    static {
        instance = new AddressService();
    }
    public static AddressService getInstance() {
        return instance;
    }

    private AddressService(){}

    public void addAddress(String sendPlace, String sendMan, String sendPhone,
                           int userId){
        User user = new UserMapper().findById(userId);
        Address address = new Address();
        address.setSendPlace(sendPlace);
        address.setSendMan(sendMan);
        address.setSendPhone(sendPhone);
        address.setUserId(userId);
        user.addDeliveryAddress(address);
    }

    public void deleteAddress(int addressId, int userId){
        User user = new UserMapper().findById(userId);
        Address address = new Address();
        address.setId(addressId);
        user.deleteDeliveryAddress(address);
    }

    public void updateAddress(int addressId, String sendPlace, String sendMan,
                              String sendPhone, int userId){
        User user = new UserMapper().findById(userId);
        Address address = new Address();
        address.setId(addressId);
        address.setSendPlace(sendPlace);
        address.setSendMan(sendMan);
        address.setSendPhone(sendPhone);
        address.setUserId(userId);
        user.updateDeliveryAddress(address);
    }

    public List<Address> viewAllAddress(int userId){
        User user = new UserMapper().findById(userId);
        List<Address> addresses = user.getDeliveryAddresses();
        return addresses;
    }

    public Address viewSingleAddress(int userId, int addressId){
        User user = new UserMapper().findById(userId);
        return user.getDeliveryAddress(addressId);
    }

}
