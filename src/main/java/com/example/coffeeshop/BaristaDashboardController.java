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
    private int selectedOrderId = -1;

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

        // TableView-তে list একবার set করবো
        orderTable.setItems(orderList);
        orderTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldOrder, newOrder) -> {

                    if (newOrder != null) {
                        selectedOrderId = newOrder.getId();

                        System.out.println(
                                "Selected Order ID: " + selectedOrderId
                        );
                    }
                });

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
            WHERE status IN ('Pending', 'Preparing', 'Completed')
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



            System.out.println(
                    "Orders loaded: " + orderList.size()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ==============================
    // START PREPARING
    // ==============================

    @FXML
    private void startPreparing() {

        if (selectedOrderId == -1) {

            System.out.println(
                    "Please select an order first!"
            );

            return;
        }

        executor.submit(() -> {

            String sql =
                    "UPDATE orders " +
                            "SET status = 'Preparing' " +
                            "WHERE id = ? " +
                            "AND status = 'Pending'";

            try (Connection connection = Database.connect();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, selectedOrderId);

                int rowsUpdated =
                        statement.executeUpdate();

                javafx.application.Platform.runLater(() -> {

                    if (rowsUpdated > 0) {

                        System.out.println(
                                "Order "
                                        + selectedOrderId
                                        + " is now Preparing."
                        );

                        loadOrders();

                    } else {

                        System.out.println(
                                "Order is not Pending."
                        );
                    }
                });

            } catch (SQLException e) {

                e.printStackTrace();
            }
        });
    }


    // ==============================
    // COMPLETE ORDER
    // ==============================

    @FXML
    private void completeOrder() {

        if (selectedOrderId == -1) {

            System.out.println(
                    "Please select an order first!"
            );

            return;
        }

        executor.submit(() -> {

            String sql =
                    "UPDATE orders " +
                            "SET status = 'Completed' " +
                            "WHERE id = ? " +
                            "AND status = 'Preparing'";

            try (Connection connection = Database.connect();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, selectedOrderId);

                int rowsUpdated =
                        statement.executeUpdate();

                javafx.application.Platform.runLater(() -> {

                    if (rowsUpdated > 0) {

                        System.out.println(
                                "Order "
                                        + selectedOrderId
                                        + " is now Completed."
                        );

                        loadOrders();

                    } else {

                        System.out.println(
                                "Order is not Preparing."
                        );
                    }
                });

            } catch (SQLException e) {

                e.printStackTrace();
            }
        });
    }


    // ==============================
    // LOGOUT
    // ==============================

    @FXML
    private void logout(ActionEvent event) throws IOException {

        if (refreshTimer != null) {
            refreshTimer.stop();
        }

        executor.shutdown();


        Parent root = FXMLLoader.load(
                getClass().getResource("hello-view.fxml")
        );


        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();


        stage.setScene(new Scene(root));

        stage.show();
    }
}