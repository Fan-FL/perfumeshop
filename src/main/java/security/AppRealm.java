package security;

import domain.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import service.ManagerService;
import service.UserService;

import java.util.HashSet;
import java.util.Set;

public class AppRealm extends JdbcRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final String username = userPassToken.getUsername();

        String accountType = AppSession.getAccountType();
        Account account = null;
        if (AppSession.USER_ROLE.equals(accountType)){
            account = UserService.getInstance().findUserByName(username);
        }else if(AppSession.MANAGER_ROLE.equals(accountType)){
            account = ManagerService.getInstance().findByName(username);
        }
        if (account == null) {
            System.out.println("No account found for user with account " + username);
            return null;
        }
        return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        if (principals.isEmpty()) {
            System.out.println("Given principals to authorize are empty.");
            return null;
        }

        String username = (String) principals.getPrimaryPrincipal();

        String accountType = AppSession.getAccountType();
        Account account = null;
        if (AppSession.USER_ROLE.equals(accountType)){
            account = UserService.getInstance().findUserByName(username);
        }else if(AppSession.MANAGER_ROLE.equals(accountType)){
            account = ManagerService.getInstance().findByName(username);
        }

        if (account == null) {
            System.out.println("No account found for account with username " + username);
            return null;
        }

        // add roles of the account according to its type
        if (AppSession.USER_ROLE.equals(accountType)){
            roles.add(AppSession.USER_ROLE);
        }else if(AppSession.MANAGER_ROLE.equals(accountType)){
            roles.add(AppSession.MANAGER_ROLE);
        }

        return new SimpleAuthorizationInfo(roles);
    }
}
