package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private void openUserManagement(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("UserManagement.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void openMenuManagement(ActionEvent event) throws IOException {
        System.out.println("Menu button clicked!");
        Parent root = FXMLLoader.load(
                getClass().getResource("MenuManagement.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void openOrderManagement(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("OrderManagement.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("hello-view.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root, 1000, 650));
        stage.show();
    }
}
