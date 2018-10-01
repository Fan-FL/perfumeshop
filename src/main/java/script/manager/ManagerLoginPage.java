package script.manager;

import controller.FrontCommand;
import domain.Manager;
import net.sf.json.JSONArray;
import service.ManagerService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerLoginPage extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ManagerService managerService = null;

    public ManagerLoginPage() {
        super();
        managerService = ManagerService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        forward("/managerLogin.jsp");
    }
}
