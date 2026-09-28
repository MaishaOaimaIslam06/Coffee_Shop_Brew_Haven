package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField mobileField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void signUp(ActionEvent event) throws IOException {

        String name = nameField.getText();
        String password = passwordField.getText();
        String mobile = mobileField.getText();

        if (name.isEmpty() || password.isEmpty() || mobile.isEmpty()) {
            System.out.println("Please fill all fields!");
            return;
        }

        boolean registered = Database.registerUser(name, password, mobile);

        if (registered) {
            CurrentUser.name = name;
            System.out.println("Sign up successful!");

            Parent root = FXMLLoader.load(
                    getClass().getResource("Checkout.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}