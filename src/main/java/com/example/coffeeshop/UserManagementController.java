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

public class UserManagementController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private ObservableList<User> userList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Connect TableView columns with User class
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        roleColumn.setCellValueFactory(
                new PropertyValueFactory<>("role")
        );

        // Load users from database
        loadUsers();
    }

    private void loadUsers() {

        String sql = """
                SELECT id, name, role
                FROM users
                """;

        try (Connection connection = Database.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            userList.clear();

            while (result.next()) {

                User user = new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("role")
                );

                userList.add(user);
            }

            userTable.setItems(userList);

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