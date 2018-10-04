package controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/FrontServlet")
public class FrontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FrontCommand command = getCommand(request);
		command.init(getServletContext(), request, response);
		command.process();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}


	private FrontCommand getCommand(HttpServletRequest request) {
    	try {
    		return (FrontCommand)getCommandClass(request).newInstance();
		}catch (Exception e){
    		e.printStackTrace();
    		return null;
		}
	}

	private Class getCommandClass(HttpServletRequest request){
    	Class result;
    	// assemable the required class name
    	final String commandClassName = "script." + (String)request.getParameter("module") + "."
				+ (String)request.getParameter("command");
    	System.out.println(commandClassName);
    	try {
    		result = Class.forName(commandClassName);
		}catch (ClassNotFoundException e){
			result  = null;
		}
		return result;
	}
}
