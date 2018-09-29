package domain;

public class Manager extends Account {
    private int id;
    private String title;

    public Manager(int id, String username, String password, String title, Money salary) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.title = title;
        this.salary = salary;
        this.type = "Manager";
    }

    public Manager(int id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.title = title;
        this.salary = salary;
        this.type = "Manager";
    }

    public Manager() {
        super();
        this.type = "Manager";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Money getSalary() {
        return salary;
    }

    public void setSalary(Money salary) {
        this.salary = salary;
    }

    private Money salary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
