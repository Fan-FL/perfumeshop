package datasource;

import domain.Address;
import domain.DomainObject;
import domain.Pager;
import domain.User;

import java.util.ArrayList;
import java.util.List;


public class AddressMapper implements IMapper{

    public static Address findAddressById(int addressId) {
        Address address = IdentityMap.addressMap.get(addressId);
        if (address != null){
            return address;
        }
        String sql = "SELECT USER_ID userId,SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE " +
                "sendPhone,ADDRESS_ID id FROM address WHERE ADDRESS_ID=?";
        address = DBHelper.getObject(Address.class, sql, addressId);
        IdentityMap.addressMap.put(addressId, address);
        return address;
    }

    @Override
    public int insert(DomainObject obj) {
        Address address = (Address)obj;
        String sql = "INSERT INTO address(SEND_MAN,SEND_PLACE,SEND_PHONE,USER_ID)VALUES(?,?,?,?)";
        int id = DBHelper.updateGetGeneratedKeys(sql, address.getSendMan(), address.getSendPlace(),
                address.getSendPhone(),
                address.getUserId());
        address.setId(id);
        IdentityMap.addressMap.put(id, address);
        return id;
    }

    @Override
    public void update(DomainObject obj) {
        Address address = (Address)obj;
        String sql = "UPDATE address SET SEND_PLACE=?,SEND_MAN=?,SEND_PHONE=? WHERE ADDRESS_ID=?";
        DBHelper.update(sql, address.getSendPlace(), address.getSendMan(), address.getSendPhone(), address.getId());
        IdentityMap.addressMap.put(address.getId(), address);
    }

    @Override
    public void delete(DomainObject obj) {
        Address address = (Address)obj;
        String sql = "DELETE FROM address WHERE ADDRESS_ID=?";
        System.out.println(address.getId());
        DBHelper.update(sql, address.getId());
        IdentityMap.addressMap.remove(address.getId());
    }

    /**
     * get all addresses of certain user
     * @param userId
     * @return
     */
    public static List<Address> getAddressByUserId(int userId) {
        String sql = "SELECT ADDRESS_ID id,SEND_PLACE sendPlace,SEND_MAN sendMan,"
                + "SEND_PHONE sendPhone FROM ADDRESS WHERE USER_ID = ?";
        List<Address> addresses = DBHelper.getObjectForList(Address.class, sql, userId);
        for(Address address: addresses){
            IdentityMap.addressMap.put(address.getId(), address);
        }
        return addresses;
    }

    /**
     * get addresses of certain user by paging
     * @param currPage
     * @param pageSize
     * @param user
     * @return
     */
    public static Pager<Address> getAddressPager(int currPage, int pageSize, User user) {
        int dataCount = user.getDeliveryAddresses().size();
        int pageCount = (dataCount % pageSize == 0) ? (dataCount / pageSize) : (dataCount / pageSize + 1);
        int dataIndex = (currPage - 1) * pageSize;
        List<Address> pageDataList = new ArrayList<>();
        List<Address> addresses = user.getDeliveryAddresses();
        for (int i = 0; i<pageSize && i< dataCount; i++) {
            Address product = addresses.get(dataIndex + i);
            pageDataList.add(product);
        }
        Pager<Address> pager = new domain.Pager<Address>(currPage, pageSize, pageCount, dataCount,
                pageDataList);
        return pager;
    }
}
