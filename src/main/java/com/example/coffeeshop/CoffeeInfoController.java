package com.example.coffeeshop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoffeeInfoController {

    @FXML
    private Label infoLabel;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    private void getCoffeeInfo() {

        infoLabel.setText("Loading coffee information...");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sampleapis.com/coffee/hot"))
                .GET()
                .build();

        client.sendAsync(
                request,
                HttpResponse.BodyHandlers.ofString()
        ).thenAccept(response -> {

            try {

                String json = response.body();

                JsonNode root = mapper.readTree(json);

                StringBuilder information = new StringBuilder();

                // Show first 8 coffee information
                for (int i = 0; i < 8 && i < root.size(); i++) {

                    JsonNode coffee = root.get(i);

                    String title = coffee.get("title").asText();

                    String description =
                            coffee.get("description").asText();

                    StringBuilder ingredients =
                            new StringBuilder();

                    for (JsonNode item : coffee.get("ingredients")) {

                        if (ingredients.length() > 0) {
                            ingredients.append(", ");
                        }

                        ingredients.append(item.asText());
                    }

                    information.append(
                            (i + 1) + ". " + title + "\n\n"
                    );

                    information.append(
                            "Description: "
                                    + description + "\n\n"
                    );

                    information.append(
                            "Ingredients: "
                                    + ingredients + "\n\n"
                    );

                    information.append(
                            "────────────────────────\n\n"
                    );
                }

                Platform.runLater(() -> {

                    infoLabel.setText(
                            information.toString()
                    );

                });

            } catch (Exception e) {

                Platform.runLater(() ->
                        infoLabel.setText(
                                "Failed to parse coffee information."
                        )
                );
            }

        }).exceptionally(error -> {

            Platform.runLater(() ->
                    infoLabel.setText(
                            "Internet connection or API error."
                    )
            );

            return null;
        });
    }


    @FXML
    private void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("AdminDashboard.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}