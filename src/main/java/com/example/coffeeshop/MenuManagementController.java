package com.example.coffeeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuManagementController {

    @FXML
    private TableView<MenuItem> menuTable;

    @FXML
    private TableColumn<MenuItem, Integer> idColumn;

    @FXML
    private TableColumn<MenuItem, String> nameColumn;

    @FXML
    private TableColumn<MenuItem, Double> priceColumn;

    @FXML
    private TextField priceField;

    private ObservableList<MenuItem> menuList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );

        menuTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldItem, newItem) -> {

                    if (newItem != null) {
                        priceField.setText(
                                String.valueOf(newItem.getPrice())
                        );
                    }
                });

        loadMenu();
    }

    private void loadMenu() {

        String sql = """
                SELECT id, name, price
                FROM menu
                """;

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            menuList.clear();

            while (result.next()) {

                MenuItem item = new MenuItem(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDouble("price")
                );

                menuList.add(item);
            }

            menuTable.setItems(menuList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updatePrice() {

        MenuItem selectedItem =
                menuTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        String priceText = priceField.getText();

        if (priceText.isEmpty()) {
            return;
        }

        double newPrice;

        try {
            newPrice = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            return;
        }

        String sql =
                "UPDATE menu SET price = ? WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newPrice);
            statement.setInt(2, selectedItem.getId());

            statement.executeUpdate();

            priceField.clear();

            loadMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("AdminDashboard.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}