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

        System.out.println("Order placed!");

    }
}