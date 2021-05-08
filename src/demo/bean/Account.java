package demo.bean;

import demo.annotation.Component;

@Component("account")
public class Account {

    long time;

    public Account(){
        time = System.currentTimeMillis();
    }
    @Override
    public String toString() {
        return "Account";
    }

    public void print() {
        System.out.println("demo.bean.Account Invoked at " + time);
    }
}
