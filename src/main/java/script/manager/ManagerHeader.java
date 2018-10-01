package script.manager;

import controller.FrontCommand;
import service.ManagerService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ManagerHeader extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ManagerService managerService = null;

    public ManagerHeader() {
        super();
        managerService = ManagerService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        include("/managerHeader.jsp");
    }
}
