package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void login(ActionEvent event) throws IOException {

        String password = passwordField.getText();

        if (password.equals("1234")) {

            Parent root = FXMLLoader.load(
                    getClass().getResource("CashierDashboard.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root, 1000, 550));
            stage.show();

        } else {

            errorLabel.setText("Incorrect Password!");
        }
    }
}