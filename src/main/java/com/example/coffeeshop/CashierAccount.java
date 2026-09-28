
package com.example.coffeeshop;

public class CashierAccount
        extends UserAccount
        implements Authenticatable {

    private String password;

    public CashierAccount(String name, String password) {
        super(name, "Cashier");
        this.password = password;
    }

    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    @Override
    public void showDashboard() {
        System.out.println(
                "Welcome Cashier " + name + "!"
        );

        System.out.println(
                "Opening Cashier Dashboard..."
        );
    }
}

