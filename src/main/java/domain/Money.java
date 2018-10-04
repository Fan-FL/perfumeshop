package domain;

/*
 * ClassName: Money
 * Description: This class contains all the details about the
 *              salary that managers can get
 */

public class Money {
    private float amount;
    private String currency;

    public Money(float amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
