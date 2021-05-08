package demo.test;

import demo.MyApplicationContext;
import demo.bean.Account;
import demo.bean.User;

public class MyTest {
    public static void main(String[] args) throws Exception {
        MyApplicationContext context = new MyApplicationContext();
        User user = (User) context.getBean("user");
        Account account = (Account) context.getBean("account");
        user.print();
        account.print();

    }
}