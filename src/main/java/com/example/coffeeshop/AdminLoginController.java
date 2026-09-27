package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private void login() {

        String name = usernameField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        if (Database.adminLogin(name, phone, password)) {
            messageLabel.setText("Login successful!");
            System.out.println("Admin login successful!");
        } else {
            messageLabel.setText("Invalid Admin information!");
        }
    }
}