package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    private void login() throws IOException {

        String name = usernameField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        if (Database.adminLogin(name, phone, password)) {

            Parent root = FXMLLoader.load(
                    getClass().getResource("AdminDashboard.fxml")
            );

            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } else {
            messageLabel.setText("Invalid Admin information!");
        }
    }
}