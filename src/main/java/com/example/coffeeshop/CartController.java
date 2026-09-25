package com.example.coffeeshop;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CartController {

    @FXML
    private GridPane cartGridPane;

    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {

        int row = 1;
        double total = 0;

        for (Coffee coffee : Cart.getItems()) {

            // Coffee name
            Label nameLabel = new Label(coffee.getName());

            // Coffee price
            Label priceLabel = new Label("৳" + coffee.getPrice());

            // Quantity
            Button minusButton = new Button("−");
            Label quantityLabel = new Label("1");
            Button plusButton = new Button("+");

            minusButton.setStyle(
                    "-fx-background-color: #FFF8EA;" +
                            "-fx-border-color: #6B3518;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 5px;" +
                            "-fx-background-radius: 5px;" +
                            "-fx-pref-width: 35px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            plusButton.setStyle(
                    "-fx-background-color: #FFF8EA;" +
                            "-fx-border-color: #6B3518;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 5px;" +
                            "-fx-pref-width: 35px;" +
                            "-fx-pref-height: 30px;" +
                            "-fx-background-radius: 5px;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            quantityLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            // Total for this item
            Label itemTotalLabel = new Label("৳" + coffee.getPrice());

            minusButton.setOnAction(event -> {

                int quantity = Integer.parseInt(quantityLabel.getText());

                if (quantity > 1) {
                    quantity--;

                    quantityLabel.setText(String.valueOf(quantity));

                    double itemTotal = coffee.getPrice() * quantity;
                    itemTotalLabel.setText("৳" + itemTotal);

                    Cart.decreaseQuantity(coffee);
                    totalLabel.setText("৳" + calculateCurrentTotal());
                }
            });

            plusButton.setOnAction(event -> {

                int quantity = Integer.parseInt(quantityLabel.getText());

                quantity++;

                quantityLabel.setText(String.valueOf(quantity));

                double itemTotal = coffee.getPrice() * quantity;
                itemTotalLabel.setText("৳" + itemTotal);

                Cart.increaseQuantity(coffee);

                totalLabel.setText("৳" + calculateCurrentTotal());
            });


            minusButton.setStyle(
                    "-fx-background-color: #FFF8EA;" +
                            "-fx-border-color: #6B3518;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 5px;" +
                            "-fx-background-radius: 5px;" +
                            "-fx-min-width: 20px;" +
                            "-fx-pref-width: 20px;" +
                            "-fx-max-width: 20px;" +
                            "-fx-min-height: 18px;" +
                            "-fx-pref-height: 18px;" +
                            "-fx-max-height: 18px;" +
                            "-fx-padding: 0;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            plusButton.setStyle(
                    "-fx-background-color: #FFF8EA;" +
                            "-fx-border-color: #6B3518;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 5px;" +
                            "-fx-background-radius: 5px;" +
                            "-fx-min-width: 20px;" +
                            "-fx-pref-width: 20px;" +
                            "-fx-max-width: 20px;" +
                            "-fx-min-height: 18px;" +
                            "-fx-pref-height: 18px;" +
                            "-fx-max-height: 18px;" +
                            "-fx-padding: 0;" +
                            "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );
            nameLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            priceLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 17px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            quantityLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 17px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-min-width: 20px;" +
                            "-fx-alignment: CENTER;" +
                            "-fx-text-fill: #6B3518;"
            );

            itemTotalLabel.setStyle(
                    "-fx-font-family: 'Georgia';" +
                            "-fx-font-size: 17px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #6B3518;"
            );

            // Add them to the GridPane
            cartGridPane.add(nameLabel, 0, row);
            cartGridPane.add(priceLabel, 1, row);
            HBox quantityBox = new HBox(5);

            quantityBox.getChildren().addAll(
                    minusButton,
                    quantityLabel,
                    plusButton
            );

            quantityBox.setAlignment(Pos.CENTER);

            cartGridPane.add(quantityBox, 2, row);
            cartGridPane.add(itemTotalLabel, 3, row);

            total += coffee.getPrice();

            row++;



        }

        // Show total amount
        totalLabel.setText("৳" + total);
    }

    private double calculateCurrentTotal() {

        double total = 0;

        for (Coffee coffee : Cart.getItems()) {
            total += coffee.getPrice();
        }

        return total;
    }
    @FXML
    private void clearCart() {

        // Clear all items from cart
        Cart.getItems().clear();

        // Remove all dynamically added items from GridPane
        cartGridPane.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        // Reset total amount
        totalLabel.setText("৳0");
    }
    @FXML
    private void backToMenu(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("Menu.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void proceedToCheckout(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("Login.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}