# Brew Haven - Coffee Shop Management System

Brew Haven is a JavaFX-based Coffee Shop Management System developed using Java. The system manages customers, menu items, orders, payments, and different staff roles through a simple desktop interface.

## Features

- Customer Sign Up
- Customer Login
- Coffee Menu
- Add items to Cart
- Order Placement
- Admin Dashboard
- User Management
- Menu Management
- Order Management
- Barista Dashboard
- Cashier Dashboard
- Order Status Management
- Payment Status Management
- Coffee Information using an external API
- SQLite Database
- Multithreading using ExecutorService
- Responsive JavaFX UI

## User Roles

### Admin
- Manage users
- Add, update and delete menu items
- Manage orders
- View coffee information

### Barista
- View customer orders
- Start preparing orders
- Complete orders
- Maximum 3 orders can be prepared at the same time

### Cashier
- View orders
- Mark orders as paid
- Process payment-related tasks

### Customer
- Sign up
- View coffee menu
- Add coffee to cart
- Place orders

## Technologies Used

- Java
- JavaFX
- Scene Builder
- SQLite
- JDBC
- Maven
- Jackson
- HTTP Client
- Git & GitHub

## Object-Oriented Programming

The project uses several OOP concepts:

- Abstraction
- Inheritance
- Encapsulation
- Polymorphism
- Interface
- Method Overriding

Example class structure:

    UserAccount
       |
       |-- AdminAccount
       |-- BaristaAccount
       |-- CashierAccount
       |-- CustomerAccount

## Database

SQLite is used to store application data.

Main tables:

- users
- menu
- orders

## Multithreading

The application uses `ExecutorService` for background tasks.

For example, database operations in the Cashier and Barista dashboards are performed using background threads so that the JavaFX user interface remains responsive.

`Platform.runLater()` is used to safely update the JavaFX UI after background operations.

## API Integration

The system uses an external Coffee API to retrieve coffee information.

JSON data is processed using the Jackson library.

## Project Structure

    CoffeeShop
    |
    |-- src
    |   |-- main
    |       |-- java
    |       |-- resources
    |
    |-- CoffeeShop.db
    |-- pom.xml
    |-- README.md

## How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure the required Java and JavaFX dependencies are available.
4. Run the main JavaFX application.
5. Use the available dashboards and features.

## Project Name

**Brew Haven - Coffee Shop Management System**

Developed as a Java/JavaFX desktop application project.
