package com.example.coffeeshop;

import com.example.coffeeshop.Database;
import javafx.application.Platform;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CashierDashboardController {

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
    private TableColumn<Order, String> paymentColumn;

    private ObservableList<Order> orderList =
            FXCollections.observableArrayList();

    // Thread pool for background database tasks
    private ExecutorService executor =
            Executors.newFixedThreadPool(2);

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

        paymentColumn.setCellValueFactory(
                new PropertyValueFactory<>("paymentStatus")
        );

        loadOrders();
    }

    private void loadOrders() {

        String sql = """
                SELECT id, customer, items, total, payment_status
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
                        "",
                        result.getString("payment_status")
                );

                orderList.add(order);
            }

            orderTable.setItems(orderList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void markAsPaid() {

        Order selectedOrder =
                orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            System.out.println("No order selected!");
            return;
        }

        int orderId = selectedOrder.getId();

        System.out.println(
                "Selected Order ID: " + orderId
        );

        // Database operation runs in background thread
        executor.submit(() -> {

            String sql =
                    "UPDATE orders SET payment_status = 'Paid' WHERE id = ?";

            try (Connection connection = Database.connect();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, orderId);

                int rowsUpdated = statement.executeUpdate();

                System.out.println(
                        "Rows updated: " + rowsUpdated
                );

                // UI update must happen on JavaFX Application Thread
                Platform.runLater(() -> {
                    loadOrders();
                    System.out.println(
                            "Payment updated successfully!"
                    );
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {

        // Stop background threads before leaving the page
        if (!executor.isShutdown()) {
            executor.shutdown();
        }

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

