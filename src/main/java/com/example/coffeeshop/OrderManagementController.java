package com.example.coffeeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementController {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, Double> totalColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    private ObservableList<Order> orderList =
            FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        customerColumn.setCellValueFactory(
                new PropertyValueFactory<>("customer")
        );

        itemsColumn.setCellValueFactory(
                new PropertyValueFactory<>("items")
        );

        totalColumn.setCellValueFactory(
                new PropertyValueFactory<>("total")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        loadOrders();
    }


    private void loadOrders() {

        String sql = """
                SELECT id, customer, items, total, status
                FROM orders
                ORDER BY id DESC
                """;

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            orderList.clear();

            while (result.next()) {

                Order order = new Order(
                        result.getInt("id"),
                        result.getString("customer"),
                        result.getString("items"),
                        result.getDouble("total"),
                        result.getString("status")
                );

                orderList.add(order);
            }

            orderTable.setItems(orderList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cancelOrder() {
        System.out.println("Cancel button clicked!");
        Order selectedOrder =
                orderTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        if (!selectedOrder.getStatus().equals("Pending")) {
            return;
        }

        String sql =
                "UPDATE orders SET status = 'Cancelled' WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, selectedOrder.getId());

            statement.executeUpdate();

            loadOrders();

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