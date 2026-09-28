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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuManagementController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private TableView<MenuItem> menuTable;

    @FXML
    private TableColumn<MenuItem, Integer> idColumn;

    @FXML
    private TableColumn<MenuItem, String> nameColumn;

    @FXML
    private TableColumn<MenuItem, Double> priceColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    private ObservableList<MenuItem> menuList =
            FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        // Responsive background
        backgroundImage.fitWidthProperty()
                .bind(rootPane.widthProperty());

        backgroundImage.fitHeightProperty()
                .bind(rootPane.heightProperty());

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

                        nameField.setText(
                                newItem.getName()
                        );

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
                ORDER BY id
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
    private void addMenuItem() {

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            return;
        }

        double price;

        try {

            price = Double.parseDouble(priceText);

            if (price <= 0) {
                return;
            }

        } catch (NumberFormatException e) {
            return;
        }

        String sql =
                "INSERT INTO menu (name, price) VALUES (?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setDouble(2, price);

            statement.executeUpdate();

            nameField.clear();
            priceField.clear();

            loadMenu();

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

        String priceText =
                priceField.getText().trim();

        if (priceText.isEmpty()) {
            return;
        }

        double newPrice;

        try {

            newPrice = Double.parseDouble(priceText);

            if (newPrice <= 0) {
                return;
            }

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

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected > 0) {

                nameField.clear();
                priceField.clear();

                loadMenu();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void deleteMenuItem() {

        MenuItem selectedItem =
                menuTable.getSelectionModel()
                        .getSelectedItem();

        // No row selected
        if (selectedItem == null) {
            System.out.println("Please select a menu item first.");
            return;
        }

        System.out.println(
                "Deleting: "
                        + selectedItem.getName()
                        + " (ID: "
                        + selectedItem.getId()
                        + ")"
        );

        String sql =
                "DELETE FROM menu WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    selectedItem.getId()
            );

            int rowsAffected =
                    statement.executeUpdate();

            System.out.println(
                    "Rows deleted: " + rowsAffected
            );

            if (rowsAffected > 0) {

                nameField.clear();
                priceField.clear();

                loadMenu();

                System.out.println("Menu item deleted successfully!");

            } else {

                System.out.println(
                        "No menu item was deleted."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBack(ActionEvent event)
            throws IOException {

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