package datasource;

import domain.Address;
import domain.Pager;

import java.util.List;


public class AddressMapper {
    public Pager<Address> getAddressPager(int currPage, int pageSize, int userId) {
        String datesql = "SELECT ADDRESS_ID addressId,SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE sendPhone,USER_ID userId FROM address WHERE USER_ID=?";
        String datecountsql = "SELECT COUNT(*) FROM address WHERE USER_ID=?";
        return new PagerHandler().getPager(Address.class, datecountsql, datesql, currPage, pageSize,
                userId);
    }

    public static void deleteAddressById(int addressId) {
        String sql = "DELETE FROM address WHERE ADDRESS_ID=?";
        DBHelper.update(sql, addressId);
    }

    public static Address getAddressById(int addressId) {
        String sql = "SELECT USER_ID userId,SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE sendPhone,ADDRESS_ID addressId FROM address WHERE ADDRESS_ID=?";
        return DBHelper.getObject(Address.class, sql, addressId);
    }

    public static void updateAddressById(Address address) {
        String sql = "UPDATE address SET SEND_PLACE=?,SEND_MAN=?,SEND_PHONE=? WHERE ADDRESS_ID=?";
        DBHelper.update(sql, address.getSendPlace(), address.getSendMan(), address.getSendPhone(), address.getAddressId());
    }

    public static int addAddress(Address address) {
        String sql = "INSERT INTO address(SEND_MAN,SEND_PLACE,SEND_PHONE,USER_ID)VALUES(?,?,?,?)";
        return DBHelper.updateGetGeneratedKeys(sql, address.getSendMan(), address.getSendPlace(),
                address.getSendPhone(),
                address.getUserId());
    }

    public static List<Address> getAddressWithUserId(int userId) {
        String sql = "SELECT ADDRESS_ID addressId,SEND_PLACE sendPlace,SEND_MAN sendMan,"
                + "SEND_PHONE sendPhone FROM ADDRESS WHERE USER_ID = ?";
        return DBHelper.getObjectForList(Address.class, sql, userId);
    }
}
