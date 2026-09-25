package com.example.coffeeshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlite:CoffeeShop.db";

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
             java.sql.Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Users table created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void registerUser(String name, String password, String mobile) {

        String sql = "INSERT INTO users(name, password, mobile) VALUES(?, ?, ?)";

        try (Connection connection = connect();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, mobile);

            statement.executeUpdate();

            System.out.println("User registered successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean loginUser(String name, String password) {

        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";

        try (Connection connection = connect();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, password);

            java.sql.ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}