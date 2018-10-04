package service;

import DTO.UserAssembler;
import DTO.UserDTO;

import java.rmi.RemoteException;

public class UserServiceBean {
    public String getUserXml(String name) throws RemoteException {
        UserDTO dto = UserDTO.getUser(name);
        return dto.toXmlString();
    }

    public void updateUser(String name, String xml) throws RemoteException {
        UserDTO dto = UserDTO.readXmlString(xml);
        new UserAssembler().updateUser(name, dto);
    }
}
