package com.example.coffeeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Label espressoPriceLabel;

    @FXML
    private Label cappuccinoPriceLabel;

    @FXML
    private Label lattePriceLabel;

    @FXML
    private Label macchiatoPriceLabel;

    @FXML
    private Label mochaPriceLabel;

    @FXML
    private Label americanoPriceLabel;

    @FXML
    private Label caramelLattePriceLabel;

    @FXML
    private Label coldBrewPriceLabel;


    @FXML
    public void initialize() {

        espressoPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Espresso"))
        );

        cappuccinoPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Cappuccino"))
        );

        lattePriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Latte"))
        );

        macchiatoPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Macchiato"))
        );

        mochaPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Mocha"))
        );

        americanoPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Americano"))
        );

        caramelLattePriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Caramel Latte"))
        );

        coldBrewPriceLabel.setText(
                String.valueOf(Database.getMenuPrice("Cold Brew"))
        );
    }


    @FXML
    private void addEspresso(ActionEvent event) {
        Cart.addItem(
                new Coffee("Espresso",
                        Database.getMenuPrice("Espresso"))
        );
    }

    @FXML
    private void addCappuccino(ActionEvent event) {
        Cart.addItem(
                new Coffee("Cappuccino",
                        Database.getMenuPrice("Cappuccino"))
        );
    }

    @FXML
    private void addLatte(ActionEvent event) {
        Cart.addItem(
                new Coffee("Latte",
                        Database.getMenuPrice("Latte"))
        );
    }

    @FXML
    private void addMacchiato(ActionEvent event) {
        Cart.addItem(
                new Coffee("Macchiato",
                        Database.getMenuPrice("Macchiato"))
        );
    }

    @FXML
    private void addMocha(ActionEvent event) {
        Cart.addItem(
                new Coffee("Mocha",
                        Database.getMenuPrice("Mocha"))
        );
    }

    @FXML
    private void addAmericano(ActionEvent event) {
        Cart.addItem(
                new Coffee("Americano",
                        Database.getMenuPrice("Americano"))
        );
    }

    @FXML
    private void addCaramelLatte(ActionEvent event) {
        Cart.addItem(
                new Coffee("Caramel Latte",
                        Database.getMenuPrice("Caramel Latte"))
        );
    }

    @FXML
    private void addColdBrew(ActionEvent event) {
        Cart.addItem(
                new Coffee("Cold Brew",
                        Database.getMenuPrice("Cold Brew"))
        );
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
    @FXML private void goBack(ActionEvent event) throws IOException { Parent root = FXMLLoader.load(
            getClass().getResource("hello-view.fxml") );
        Stage stage = (Stage) ((Node) event.getSource()) .getScene() .getWindow();
        stage.setScene(new Scene(root, 1000, 650)); stage.show();
    }
}