package com.example.coffeeshop;

public class Order {

    private int id;
    private String customer;
    private String items;
    private double total;
    private String status;


    public Order(int id, String customer, String items,
                 double total, String status) {

        this.id = id;
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.status = status;
    }

    private String paymentStatus;
    public Order(int id, String customer, String items,
                 double total, String status, String paymentStatus) {

        this.id = id;
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}