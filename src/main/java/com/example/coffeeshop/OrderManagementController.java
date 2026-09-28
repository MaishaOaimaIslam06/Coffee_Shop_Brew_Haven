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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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

    private Timeline refreshTimer;

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

        // First time load
        loadOrders();

        // Automatically refresh every 2 seconds
        refreshTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(2),
                        event -> loadOrders()
                )
        );

        refreshTimer.setCycleCount(Timeline.INDEFINITE);
        refreshTimer.play();
    }


    private void loadOrders() {
        System.out.println("Loading orders...");
        System.out.println(
                "Database location: "
                        + new java.io.File("CoffeeShop.db")
                        .getAbsolutePath()
        );
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

        if (refreshTimer != null) {
            refreshTimer.stop();
        }

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