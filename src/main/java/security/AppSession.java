package security;

import domain.Account;
import domain.Manager;
import domain.User;
import org.apache.shiro.SecurityUtils;

public class AppSession {

    public static final String ACCOUNT_ATTRIBUTE_NAME = "account";
    public static final String USER_ROLE = "user";
    public static final String MANAGER_ROLE = "manager";
    public static final String ACOUNT_TYPE_ATTRIBUTE_NAME = "account type";

    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static void init(Account account) {
        SecurityUtils.getSubject().getSession().setAttribute(ACCOUNT_ATTRIBUTE_NAME, account);
        if (account instanceof User){
            SecurityUtils.getSubject().getSession().setAttribute(ACOUNT_TYPE_ATTRIBUTE_NAME, USER_ROLE);
        }else if(account instanceof Manager){
            SecurityUtils.getSubject().getSession().setAttribute(ACOUNT_TYPE_ATTRIBUTE_NAME, MANAGER_ROLE);
        }
    }

    public static Account getAccount() {
        return (Account) SecurityUtils.getSubject().getSession().getAttribute(ACCOUNT_ATTRIBUTE_NAME);
    }

    public static int getId(){
        Account accout = (Account)SecurityUtils.getSubject().getSession().getAttribute
                (ACCOUNT_ATTRIBUTE_NAME);
        return accout.getId();
    }

    public static void setAccountType(String userRole) {
        SecurityUtils.getSubject().getSession().setAttribute(ACOUNT_TYPE_ATTRIBUTE_NAME, userRole);
    }

    public static String getAccountType(){
       return (String) SecurityUtils.getSubject().getSession().getAttribute(AppSession
                .ACOUNT_TYPE_ATTRIBUTE_NAME);
    }
}

