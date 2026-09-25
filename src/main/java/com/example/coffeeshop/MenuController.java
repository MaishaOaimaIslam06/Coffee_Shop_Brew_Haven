package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private void addEspresso(ActionEvent event) {
        Cart.addItem(new Coffee("Espresso", 150));
        System.out.println("Espresso added to cart!");
    }

    @FXML
    private void addCappuccino(ActionEvent event) {
        Cart.addItem(new Coffee("Cappuccino", 180));
        System.out.println("Cappuccino added to cart!");
    }

    @FXML
    private void addLatte(ActionEvent event) {
        Cart.addItem(new Coffee("Latte", 200));
        System.out.println("Latte added to cart!");
    }

    @FXML
    private void addMacchiato(ActionEvent event) {
        Cart.addItem(new Coffee("Macchiato", 170));
        System.out.println("Macchiato added to cart!");
    }

    @FXML
    private void addMocha(ActionEvent event) {
        Cart.addItem(new Coffee("Mocha", 220));
        System.out.println("Mocha added to cart!");
    }

    @FXML
    private void addAmericano(ActionEvent event) {
        Cart.addItem(new Coffee("Americano", 160));
        System.out.println("Americano added to cart!");
    }

    @FXML
    private void addCaramelLatte(ActionEvent event) {
        Cart.addItem(new Coffee("Caramel Latte", 230));
        System.out.println("Caramel Latte added to cart!");
    }

    @FXML
    private void addColdBrew(ActionEvent event) {
        Cart.addItem(new Coffee("Cold Brew", 190));
        System.out.println("Cold Brew added to cart!");
    }

    @FXML
    private void viewCart(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("Cart.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}