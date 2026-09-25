package com.example.coffeeshop;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField mobileField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void signUp() {

        String name = nameField.getText();
        String password = passwordField.getText();
        String mobile = mobileField.getText();

        if (name.isEmpty() || password.isEmpty() || mobile.isEmpty()) {

            System.out.println("Please fill all fields!");

            return;
        }

        Database.registerUser(name, password, mobile);

        System.out.println("Sign up successful!");
    }
}