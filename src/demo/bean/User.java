package demo.bean;

import demo.annotation.Autowired;
import demo.annotation.Component;
import demo.annotation.Value;

@Component("user")
public class User {

    @Value("samsara")
    private String name;
    @Value("18")
    private String age;
//    @Autowired
    private Account account;

    public User(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", account=" + account +
                '}';
    }

    public void print(){
        System.out.println("demo.bean.User Invoked!");
        System.out.println(this);
        account.print();
    }
}
