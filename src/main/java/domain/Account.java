package domain;

/*
 * ClassName: Account
 * Description: The Account class is a super class for the User class and Manager class.
 *              It contains the attributes including username, password and type.
 */

public class Account extends DomainObject {
    protected String username;
    protected String password;
    protected String type;

    /*
     * Parameters: None
     * Return:type
     * Description: there are two account types, user and manager
     * */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*
     * Parameters: None
     * Return:user's name
     * Description: Each account has his or her username
     * */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Parameters: None
     * Return:user's password
     * Description: Each account has his or her password
     * */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
