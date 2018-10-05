package DTO;

import domain.*;
import service.UserService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserAssembler {
    public UserDTO writeDTO(User subject) {
        UserDTO result = new UserDTO();
        result.setId(subject.getId());
        result.setAddress(subject.getAddress());
        result.setPhone(subject.getPhone());
        result.setUsername(subject.getUsername());
        writeAddresses(result, subject);
        writeOrders(result, subject);
        writeCarts(result, subject);
        return result;
    }

    private void writeAddresses(UserDTO userDTO, User subject) {
        List<AddressDTO> addresses = new ArrayList<AddressDTO>();
        Iterator<Address> it = subject.getDeliveryAddresses().iterator();
        while (it.hasNext()) {
            AddressDTO newDTO = new AddressDTO();
            Address thisAddress = it.next();
            newDTO.setSendPhone(thisAddress.getSendPhone());
            newDTO.setSendMan(thisAddress.getSendMan());
            newDTO.setSendPlace(thisAddress.getSendPlace());
            addresses.add(newDTO);
        }
        userDTO.setAddresses(addresses);
    }

    private void writeOrders(UserDTO userDTO, User subject) {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        Iterator<OrderMsg> it = subject.getOrderMsgs().iterator();
        while (it.hasNext()) {
            OrderMsg thisOrderMsg = it.next();
            for (OrderProduct orderProduct: thisOrderMsg.getProduct()){
                OrderDTO newDTO = new OrderDTO();
                newDTO.setOrderNum(thisOrderMsg.getOrderNum());
                newDTO.setSendPhone(thisOrderMsg.getSendPhone());
                newDTO.setSendMan(thisOrderMsg.getSendMan());
                newDTO.setSendPlace(thisOrderMsg.getSendPlace());
                newDTO.setOrderTime(thisOrderMsg.getOrderTime());
                newDTO.setProductName(orderProduct.getProductName());
                newDTO.setSaleCount(orderProduct.getSaleCount());
                newDTO.setProductPrice(orderProduct.getSaleCount());
                newDTO.setProductId(orderProduct.getProductId());
                orders.add(newDTO);
            }
        }
        userDTO.setOrders(orders);
    }

    private void writeCarts(UserDTO userDTO, User subject) {
        List<CartDTO> carts = new ArrayList<CartDTO>();
        Iterator<CartItem> it = subject.getCartItems().iterator();
        while (it.hasNext()) {
            CartDTO newDTO = new CartDTO();
            CartItem thisCartItem = it.next();
            newDTO.setSaleCount(thisCartItem.getSaleCount());
            newDTO.setProductId(thisCartItem.getProductId());
            carts.add(newDTO);
        }
        userDTO.setCarts(carts);
    }

    public void updateUser(UserDTO dto) {
        User user = UserService.getInstance().findUserByName(dto.getUsername());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setTruename(dto.getUsername());
        UserService.getInstance().updateUser(user.getId(),user.getPassword(),user.getTruename(),
                user.getPhone(),user.getAddress());
    }
}