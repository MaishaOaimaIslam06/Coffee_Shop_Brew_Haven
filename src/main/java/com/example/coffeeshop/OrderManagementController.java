package com.example.coffeeshop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView backgroundImage;

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

        // Responsive background
        backgroundImage.fitWidthProperty()
                .bind(rootPane.widthProperty());

        backgroundImage.fitHeightProperty()
                .bind(rootPane.heightProperty());


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


        // First load
        loadOrders();


        // Refresh every 2 seconds
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

        Order selectedOrder =
                orderTable.getSelectionModel()
                        .getSelectedItem();


        // No order selected
        if (selectedOrder == null) {

            showMessage(
                    "No Order Selected",
                    "Please select an order first."
            );

            return;
        }


        // Only Pending orders can be cancelled
        if (!selectedOrder.getStatus().equals("Pending")) {

            showMessage(
                    "Cannot Cancel",
                    "Only Pending orders can be cancelled."
            );

            return;
        }


        String sql =
                "UPDATE orders SET status = 'Cancelled' WHERE id = ?";


        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    selectedOrder.getId()
            );

            int rowsAffected =
                    statement.executeUpdate();


            if (rowsAffected > 0) {

                loadOrders();

                System.out.println(
                        "Order "
                                + selectedOrder.getId()
                                + " cancelled."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showMessage(
            String title,
            String message) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }


    @FXML
    private void goBack(ActionEvent event)
            throws IOException {

        // Stop automatic refresh
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