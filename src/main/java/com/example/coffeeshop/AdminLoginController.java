package com.example.coffeeshop;

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
    private void login() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (Database.adminLogin(username, password)) {

            messageLabel.setText("Login successful!");

            System.out.println("Admin login successful!");

        } else {

            messageLabel.setText(
                    "Invalid Admin username or password!"
            );
        }
    }
}