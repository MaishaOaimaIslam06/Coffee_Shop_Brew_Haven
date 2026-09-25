package com.example.coffeeshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private void login() {

        String name = nameField.getText();
        String password = passwordField.getText();

        if (Database.loginUser(name, password)) {

            System.out.println("Login successful!");

        } else {

            System.out.println("Invalid name or password!");
        }
    }

    @FXML
    private void openSignUp() throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("SignUp.fxml")
        );

        Stage stage = (Stage) signUpButton
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}