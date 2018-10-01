package session;


import domain.Manager;
import domain.User;

import javax.servlet.http.HttpSession;

public class SessionManager {

    private static final String USER_ATTRIBUTE_NAME = "user";
    private static final String MANAGER_ATTRIBUTE_NAME = "manager";

    private HttpSession httpSession;

    private SessionManager(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public static SessionManager getSession(HttpSession httpSession) {
        httpSession.setMaxInactiveInterval(1800);
        return new SessionManager(httpSession);
    }

    public User getUser() {
       return (User) this.httpSession.getAttribute(USER_ATTRIBUTE_NAME);
    }

    public Manager getManager() {
        return (Manager) this.httpSession.getAttribute(MANAGER_ATTRIBUTE_NAME);
    }

    public void setUser(User user) {
        this.httpSession.setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    public void setManager(Manager manager) {
        this.httpSession.setAttribute(MANAGER_ATTRIBUTE_NAME, manager);
    }
}

