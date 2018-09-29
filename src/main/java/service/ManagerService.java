package service;

import datasource.ManagerMapper;
import domain.Manager;

public class ManagerService {
    private static ManagerService instance;
    static {
        instance = new ManagerService();
    }
    public static ManagerService getInstance() {
        return instance;
    }

    private ManagerService(){}

    public Manager findByName(String managerName) {
        return ManagerMapper.findByName(managerName);
    }
}
