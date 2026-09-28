package com.example.coffeeshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Database.testConnection();
        Database.createTables();
        Database.createDefaultAdmin();
        Database.insertDefaultMenu();


        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Coffee Shop");
        stage.setScene(scene);

        stage.setMinWidth(800);
        stage.setMinHeight(500);

        stage.setResizable(true);

        stage.show();
    }
}
