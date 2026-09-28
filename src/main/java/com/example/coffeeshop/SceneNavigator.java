package com.example.coffeeshop;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {

    /**
     * Switches the scene root using an Event while preserving stage maximized state and window dimensions.
     */
    public static void switchScene(Event event, String fxmlFile) throws IOException {
        switchScene((Node) event.getSource(), fxmlFile);
    }

    /**
     * Switches the scene root using any Node while preserving stage maximized state and window dimensions.
     */
    public static void switchScene(Node node, String fxmlFile) throws IOException {
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlFile));

        Scene scene = stage.getScene();
        if (scene == null) {
            stage.setScene(new Scene(root, 1000, 650));
        } else {
            scene.setRoot(root);
        }
        stage.show();
    }
}
