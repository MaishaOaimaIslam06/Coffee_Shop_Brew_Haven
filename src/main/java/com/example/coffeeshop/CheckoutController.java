package com.example.coffeeshop;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CheckoutController {

    @FXML
    private TextField addressField;

    @FXML
    private RadioButton codRadio;

    @FXML
    private RadioButton onlineRadio;

    @FXML
    private RadioButton bkashRadio;

    @FXML
    private RadioButton nagadRadio;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField pinField;

    @FXML
    private void placeOrder() {

        // Check whether a user is logged in
        if (CurrentUser.name == null || CurrentUser.name.trim().isEmpty()) {
            System.out.println("No customer is logged in.");
            return;
        }

        String customer = CurrentUser.name.trim();

        StringBuilder items = new StringBuilder();

        for (Coffee coffee : Cart.getItems()) {

            if (items.length() > 0) {
                items.append(", ");
            }

            items.append(coffee.getName());
        }

        // Check whether cart is empty
        if (items.length() == 0) {
            System.out.println("Cart is empty.");
            return;
        }

        double total = Cart.getTotal();

        // Payment status
        String paymentStatus;

        if (codRadio.isSelected()) {
            paymentStatus = "Unpaid";
        } else {
            paymentStatus = "Paid";
        }

        // Save order
        boolean saved = Database.saveOrder(
                customer,
                items.toString(),
                total,
                paymentStatus
        );

        // Clear cart only if order was successfully saved
        if (saved) {
            Cart.clearCart();

            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Order could not be placed.");
        }
    }
}