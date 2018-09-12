package datasource;

import domain.Account;
import domain.DomainObject;


public class AccountMapper implements IMapper{

    public static Account findByID(Account obj) {
        if(obj.getType().equals("manager")){
            return ManagerMapper.findByID(obj.getId());
        }else{
            return UserMapper.findByID(obj.getId());
        }
    }

    @Override
    public int insert(DomainObject obj) {
        Account manager = (Account) obj;
        if(manager.getType().equals("manager")){
            return new ManagerMapper().insert(manager);
        }else{
            return new UserMapper().insert(obj);
        }
    }

    @Override
    public void update(DomainObject obj) {
        Account manager = (Account)obj;
        if(manager.getType().equals("manager")){
            new ManagerMapper().update(manager);
        }else{
            new UserMapper().update(obj);
        }
    }

    @Override
    public void delete(DomainObject obj) {
        Account manager = (Account)obj;
        if(manager.getType().equals("manager")){
            new ManagerMapper().delete(manager);
        }else{
            new UserMapper().delete(obj);
        }
    }
}
