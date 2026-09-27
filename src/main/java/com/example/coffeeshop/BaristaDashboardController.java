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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BaristaDashboardController {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    private ObservableList<Order> orderList =
            FXCollections.observableArrayList();


    private ExecutorService executor =
            Executors.newFixedThreadPool(3);

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

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        loadOrders();

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

        String sql = """
                SELECT id, customer, items, status
                FROM orders
                WHERE status != 'Cancelled'
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
                        0,
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
    private void startPreparing() {

        Order selectedOrder =
                orderTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        if (!selectedOrder.getStatus().equals("Pending")) {
            return;
        }

        executor.submit(() -> {

            String sql =
                    "UPDATE orders SET status = 'Preparing' WHERE id = ?";

            try (Connection connection = Database.connect();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, selectedOrder.getId());

                statement.executeUpdate();

                javafx.application.Platform.runLater(() -> {
                    loadOrders();
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void completeOrder() {

        Order selectedOrder =
                orderTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        if (!selectedOrder.getStatus().equals("Preparing")) {
            return;
        }

        executor.submit(() -> {

            String sql =
                    "UPDATE orders SET status = 'Completed' WHERE id = ?";

            try (Connection connection = Database.connect();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, selectedOrder.getId());

                statement.executeUpdate();

                javafx.application.Platform.runLater(() -> {
                    loadOrders();
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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