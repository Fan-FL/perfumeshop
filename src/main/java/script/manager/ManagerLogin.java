package script.manager;

import controller.FrontCommand;
import domain.Manager;
import net.sf.json.JSONArray;
import service.ManagerService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerLogin extends FrontCommand {
	private static final long serialVersionUID = 1L;

    ManagerService managerService = null;

    public ManagerLogin() {
        super();
        managerService = ManagerService.getInstance();
    }

    @Override
    public void process() throws ServletException, IOException {
        String managerName = request.getParameter("managerName");
        String managerPassword = request.getParameter("managerPassword");
        Manager manager = managerService.findByName(managerName);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String jsonStr = null;
            if (manager == null || !managerPassword.equals(manager.getPassword())) {
                jsonStr = "[{'check':'checkout'}]";
            } else {
                jsonStr = "[{'check':'checkin'}]";
                request.getSession().setAttribute("manager", manager);
            }
            JSONArray json = JSONArray.fromObject(jsonStr);
            out.write(json.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
