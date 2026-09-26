package com.example.coffeeshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:CoffeeShop.db";

    // Database connection
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    public static void testConnection() {

        try (Connection connection = connect()) {

            System.out.println("Database connected successfully!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Create table
    public static void createTables() {

        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    password TEXT NOT NULL,
                    mobile TEXT NOT NULL
                );
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

            System.out.println("Users table ready!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Register new user
    public static boolean registerUser(String name, String password, String mobile) {

        String sql = """
                INSERT INTO users(name, password, mobile)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, mobile);

            statement.executeUpdate();

            System.out.println("New user registered: " + name);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login
    public static boolean loginUser(String name, String password) {

        String sql = """
                SELECT id
                FROM users
                WHERE name = ? AND password = ?
                """;

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Show all users - for testing
    public static void showAllUsers() {

        String sql = "SELECT * FROM users";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            System.out.println("------ ALL USERS ------");

            while (result.next()) {

                System.out.println(
                        "ID: " + result.getInt("id") +
                                " | Name: " + result.getString("name") +
                                " | Mobile: " + result.getString("mobile")
                );
            }

            System.out.println("-----------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}