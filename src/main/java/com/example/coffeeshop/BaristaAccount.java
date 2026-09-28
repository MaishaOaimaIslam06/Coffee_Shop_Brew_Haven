
package com.example.coffeeshop;

public class BaristaAccount
        extends UserAccount
        implements Authenticatable {

    private String password;

    public BaristaAccount(String name, String password) {
        super(name, "Barista");
        this.password = password;
    }

    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    @Override
    public void showDashboard() {
        System.out.println(
                "Welcome Barista " + name + "!"
        );

        System.out.println(
                "Opening Barista Dashboard..."
        );
    }
}

