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

        String customer = CurrentUser.name;

        StringBuilder items = new StringBuilder();

        for (Coffee coffee : Cart.getItems()) {

            if (items.length() > 0) {
                items.append(", ");
            }

            items.append(coffee.getName());
        }

        double total = Cart.getTotal();

        Database.saveOrder(
                customer,
                items.toString(),
                total
        );

        Cart.clearCart();

        System.out.println("Order placed successfully!");
    }
}