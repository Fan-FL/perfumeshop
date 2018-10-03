package service;

import DTO.UserAssembler;
import DTO.UserDTO;
import datasource.UserMapper;

import java.rmi.RemoteException;

public class UserServiceBean {
    public UserDTO getUser(String name) throws RemoteException {
        return new UserAssembler().writeDTO(UserMapper.findByName(name));
    }

    public String getUserXml(String name) throws RemoteException {
        UserDTO dto = getUser(name);
        return dto.toXmlString();
    }

    public void updateUser(String name, UserDTO dto) throws RemoteException {
        new UserAssembler().updateUser(name, dto);
    }
    public void updateUser(String name, String xml) throws RemoteException {
        UserDTO dto = UserDTO.readXmlString(xml);
        updateUser(name, dto);
    }
}
