
package com.example.coffeeshop;

public class AdminAccount
        extends UserAccount
        implements Authenticatable {

    private String password;

    public AdminAccount(String name, String password) {
        super(name, "Admin");
        this.password = password;
    }

    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    @Override
    public void showDashboard() {
        System.out.println(
                "Welcome Admin " + name + "!"
        );

        System.out.println(
                "Opening Admin Dashboard..."
        );
    }
}

