package domain;

/*
 * ClassName: Address
 * Description: The Address class contains all the details about the address of the user.
 */

public class Address extends DomainObject{
	private int id;
	private String sendPlace;
	private String sendMan;
	private String sendPhone;
	private int userId;
	public Address(int id, String sendPlace, String sendMan,
                   String sendPhone, int userId) {
		super();
		this.id = id;
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
		this.userId = userId;
	}
	public Address(String sendPlace, String sendMan, String sendPhone,
                   int userId) {
		super();
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
		this.userId = userId;
	}

    public Address() {
        super();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getSendPlace() {
        return sendPlace;
    }

	public void setSendPlace(String sendPlace) {
		this.sendPlace = sendPlace;
	}

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Address [addressId=" + id + ", sendPlace=" + sendPlace
                + ", sendMan=" + sendMan + ", sendPhone=" + sendPhone
                + ", userId=" + userId + "]";
    }
}
