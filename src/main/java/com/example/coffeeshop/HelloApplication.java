package com.example.coffeeshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);

        stage.setTitle("Coffee Shop");
        stage.setScene(scene);

        stage.setResizable(true);   // window resize করা যাবে
        stage.show();
    }
}
