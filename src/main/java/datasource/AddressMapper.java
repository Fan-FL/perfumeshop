package datasource;

import domain.Address;
import domain.DomainObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AddressMapper implements IMapper{

    @Override
    public Address findById(int addressId) {
        Address address = IdentityMap.addressMap.get(addressId);
        if (address != null){
            return address;
        }
        String sql = "SELECT USER_ID AS userid,SEND_PLACE AS sendPlace,SEND_MAN AS sendMan,SEND_PHONE " +
                "AS sendPhone FROM perfume.address WHERE ADDRESS_ID=?";

        PreparedStatement ps = null;
        ResultSet rs  = null;
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, addressId);
            rs = ps.executeQuery();
            if (rs.next()){
                int userid = rs.getInt(1);
                String sendPlace = rs.getString(2);
                String sendMan = rs.getString(3);
                String sendPhone = rs.getString(4);
                address = new Address(sendPlace, sendMan, sendPhone, userid);
                IdentityMap.addressMap.put(addressId, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }
        return address;
    }

    @Override
    public int insert(DomainObject obj) {
        Address address = (Address)obj;
        String sql = "INSERT INTO perfume.address (SEND_MAN,SEND_PLACE,SEND_PHONE,USER_ID) VALUES" +
                " " +
                "(?,?,?,?)";
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
        String sql = "UPDATE perfume.address SET SEND_PLACE=?,SEND_MAN=?,SEND_PHONE=? WHERE ADDRESS_ID=?";
        DBHelper.update(sql, address.getSendPlace(), address.getSendMan(), address.getSendPhone(), address.getId());
        Address inMap = IdentityMap.addressMap.get(address.getId());
        inMap.setUserId(address.getUserId());
        inMap.setSendPhone(address.getSendPhone());
        inMap.setSendMan(address.getSendMan());
        inMap.setSendPlace(address.getSendPlace());
    }

    @Override
    public void delete(DomainObject obj) {
        Address address = (Address)obj;
        String sql = "DELETE FROM perfume.address WHERE ADDRESS_ID=?";
        DBHelper.update(sql, address.getId());
        IdentityMap.addressMap.remove(address.getId());
    }

    /**
     * get all addresses of certain user
     * @param userid
     * @return
     */
    public static List<Address> getAddressByUserId(int userid) {
        String sql = "SELECT ADDRESS_ID AS id,SEND_PLACE AS sendPlace,SEND_MAN AS sendMan,"
                + "SEND_PHONE AS sendPhone FROM perfume.ADDRESS WHERE USER_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs  = null;
        List<Address> addresses = new ArrayList<Address>();
        try {
            ps = DBConnection.prepare(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String sendPlace = rs.getString(2);
                String sendMan = rs.getString(3);
                String sendPhone = rs.getString(4);
                Address address = IdentityMap.addressMap.get(id);
                if(address == null){
                    address = new Address(id, sendPlace, sendMan, sendPhone, userid);
                    IdentityMap.addressMap.put(id, address);
                }
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.release(ps, null, rs);
        }

        return addresses;
    }
}
