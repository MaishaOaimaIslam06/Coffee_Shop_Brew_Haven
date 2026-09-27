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
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                password TEXT NOT NULL,
                mobile TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'Customer'
            );
            """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(createUsersTable);

            // Add role column to an existing database
            try {
                statement.execute(
                        "ALTER TABLE users ADD COLUMN role TEXT NOT NULL DEFAULT 'Customer'"
                );
            } catch (SQLException ignored) {
                // role column already exists
            }

            System.out.println("Users table ready!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDefaultAdmin() {

        String checkSql = """
            SELECT id FROM users
            WHERE role = 'Admin'
            LIMIT 1
            """;

        String updateSql = """
            UPDATE users
            SET name = ?, password = ?, mobile = ?, role = 'Admin'
            WHERE id = ?
            """;

        String insertSql = """
            INSERT INTO users(name, password, mobile, role)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection connection = connect();
             PreparedStatement checkStatement =
                     connection.prepareStatement(checkSql);
             ResultSet result = checkStatement.executeQuery()) {

            if (result.next()) {

                // Admin already exists → make it fixed
                int adminId = result.getInt("id");

                try (PreparedStatement updateStatement =
                             connection.prepareStatement(updateSql)) {

                    updateStatement.setString(1, "Oaima");
                    updateStatement.setString(2, "1234");
                    updateStatement.setString(3, "01992000000");
                    updateStatement.setInt(4, adminId);

                    updateStatement.executeUpdate();
                }

            } else {

                // No Admin exists → create fixed Admin
                try (PreparedStatement insertStatement =
                             connection.prepareStatement(insertSql)) {

                    insertStatement.setString(1, "Oaima");
                    insertStatement.setString(2, "1234");
                    insertStatement.setString(3, "01992000000");
                    insertStatement.setString(4, "Admin");

                    insertStatement.executeUpdate();
                }
            }

            System.out.println("Fixed Admin account ready!");

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

    public static boolean adminLogin(String name, String phone, String password) {

        String sql = """
            SELECT id
            FROM users
            WHERE name = ?
              AND mobile = ?
              AND password = ?
              AND role = 'Admin'
            """;

        try (Connection connection = connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, password);

            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}