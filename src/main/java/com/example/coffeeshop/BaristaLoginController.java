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

public class BaristaLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void login(ActionEvent event) throws IOException {

        String password = passwordField.getText();

        if (password.equals("1234")) {

            Parent root = FXMLLoader.load(
                    getClass().getResource("BaristaDashboard.fxml")
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
    @FXML
    private void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("hello-view.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}