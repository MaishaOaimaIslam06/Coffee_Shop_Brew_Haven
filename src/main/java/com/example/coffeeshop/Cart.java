package com.example.coffeeshop;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static final List<Coffee> items = new ArrayList<>();

    public static void addItem(Coffee coffee) {
        items.add(coffee);
    }

    public static List<Coffee> getItems() {
        return items;
    }

    public static double getTotal() {
        double total = 0;

        for (Coffee coffee : items) {
            total += coffee.getPrice();
        }

        return total;
    }

    public static void increaseQuantity(Coffee coffee) {
        items.add(coffee);
    }

    public static void decreaseQuantity(Coffee coffee) {
        items.remove(coffee);
    }

    public static void clearCart() {
        items.clear();
    }
}