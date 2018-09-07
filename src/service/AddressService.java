package service;

import datasource.AddressMapper;
import datasource.UserMapper;
import domain.Address;
import domain.Pager;
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
        User user = UserMapper.findByID(userId);
        Address address = new Address();
        address.setSendPlace(sendPlace);
        address.setSendMan(sendMan);
        address.setSendPhone(sendPhone);
        address.setUserId(userId);
        user.addDeliveryAddress(address);
    }

    public void deleteAddress(int addressId, int userId){
        User user = UserMapper.findByID(userId);
        Address address = new Address();
        address.setId(addressId);
        user.deleteDeliveryAddress(address);
    }

    public void updateAddress(int addressId, String sendPlace, String sendMan,
                              String sendPhone, int userId){
        User user = UserMapper.findByID(userId);
        Address address = new Address();
        address.setId(addressId);
        address.setSendPlace(sendPlace);
        address.setSendMan(sendMan);
        address.setSendPhone(sendPhone);
        address.setUserId(userId);
        user.updateDeliveryAddress(address);
    }

    public Pager<Address> viewAllAddress(int currPage, int userId){
        User user = UserMapper.findByID(userId);
        user.getDeliveryAddresses();
        Pager<Address> pager = AddressMapper.getAddressPager(currPage, 4,
                user);
        return pager;
    }

    public Address viewSingleAddress(int userId, int addressId){
        User user = UserMapper.findByID(userId);
        return user.getDeliveryAddress(addressId);
    }

}
