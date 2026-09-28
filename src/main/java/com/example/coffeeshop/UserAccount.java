package com.example.coffeeshop;

public abstract class UserAccount {

    protected String name;
    protected String role;

    public UserAccount(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public abstract void showDashboard();
}