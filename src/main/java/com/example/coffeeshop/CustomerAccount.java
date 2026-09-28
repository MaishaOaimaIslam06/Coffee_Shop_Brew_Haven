package com.example.coffeeshop;

public class CustomerAccount
        extends UserAccount
        implements Authenticatable {

    private String password;

    public CustomerAccount(String name, String password) {

        super(name, "Customer");

        this.password = password;
    }

    @Override
    public boolean login(String password) {

        return this.password.equals(password);
    }

    @Override
    public void showDashboard() {

        System.out.println(
                "Welcome " + name + "!"
        );

        System.out.println(
                "Opening Customer Dashboard..."
        );
    }
}